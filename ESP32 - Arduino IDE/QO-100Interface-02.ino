


// versione 01

#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include <ArduinoJson.h>

// for DHT22
#include <DHT.h>

// for DS18B20
#include <OneWire.h>
#include <DallasTemperature.h>

// update of the parameters in milliseconds
const int cycleTime = 1000;  // in millisecondi

// enter wifi parameters ( to be improved)
const char* ssid = "SKYAP2";
const char* password = "aaabbbcccddd";

// create the async server on port 80
AsyncWebServer server(80);
AsyncWebSocket ws("/ws");

// ===== PIN =====

const int Vin = 1;
const int Vpower = 2;
const int Apower = 3;
const int Win = 4;
const int Wout = 5;
const int Wref = 6;

const int ONOFF = 7;
const int DATV = 8;
const int GPSDO = 9;

const int DHT1_PIN = 10;
const int DS18B20 = 11;
const int FAN = 12;
const int OPEN5 = 13;
const int OPEN6 = 14;



// DHT22 temperature and umidity (air) definition
#define DHTTYPE DHT22
DHT dht1(DHT1_PIN, DHTTYPE);

//DS18B20 temperature probe (P.A.) definition
#define ONE_WIRE_BUS DS18B20
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);



// ===== STATO =====
StaticJsonDocument<512> stato;

// ===== UPDATE HARDWARE =====
void aggiornaRelay() {
  digitalWrite(ONOFF, stato["onOff"] ? HIGH : LOW);
  digitalWrite(DATV, stato["datv"] ? HIGH : LOW);
  digitalWrite(GPSDO, stato["gpsdo"] ? HIGH : LOW);
  digitalWrite(FAN, stato["fan"] ? HIGH : LOW);
  digitalWrite(OPEN5, stato["open5"] ? HIGH : LOW);
  digitalWrite(OPEN6, stato["open6"] ? HIGH : LOW);

  // ad other digital controls
}

// read all input
void aggiornaIngressi() {

  stato["Vin"] = analogRead(Vin);
  stato["Vpower"] = analogRead(Vpower);
  stato["Apower"] = analogRead(Apower);
  stato["Win"] = analogRead(Win); 
  stato["Wout"] = analogRead(Wout);
  stato["Wref"] = analogRead(Wref);

  sensors.requestTemperatures();
  float tempPA = sensors.getTempCByIndex(0);

  float t1 = dht1.readTemperature();
  float h1 = dht1.readHumidity();
 

  if (!isnan(t1) && !isnan(h1)) {
    //stato["dht10"]["t"] = t1;
    //stato["dht10"]["h"] = h1;
    stato["Tair"] = t1;
    stato["Uair"] = h1;

  }
  if (!isnan(tempPA) ) {
     stato["Tpower"] = tempPA;
 }
}

// ===== PUSH STATO =====
// send data read to JSON
void broadcastStato() {
  String json;
  serializeJson(stato, json);
  ws.textAll(json);
  Serial.println(json);
}

// ===== WS EVENTS =====
// everytime a new valid event is detected write it into JSON 
void onWsEvent(AsyncWebSocket *server,
               AsyncWebSocketClient *client,
               AwsEventType type,
               void *arg,
               uint8_t *data,
               size_t len) {

  if (type == WS_EVT_CONNECT) {
    broadcastStato();
  }
}

// ===== INITIAL SETUP =====
void setup() {
  
  // start serial port
  Serial.begin(115200);

    // start reading sensors
  dht1.begin();
  sensors.begin();
  
  // define pi scope
  pinMode(ONOFF, OUTPUT);
  pinMode(DATV, OUTPUT);
  pinMode(GPSDO, OUTPUT);
  pinMode(FAN, OUTPUT);
  pinMode(OPEN5, OUTPUT);
  pinMode(OPEN6, OUTPUT);

  // all off when started and update
  stato["onOff"] = false;
  stato["datv"] = false;
  stato["gpsdo"] = false;
  stato["fan"] = false;
  stato["open5"] = false;
  stato["open6"] = false;


  aggiornaRelay();

  //start wifi with a little delay before proceeding
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) delay(500);
  
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // start Webserver with the defined onWsEvent function and the right soket
  ws.onEvent(onWsEvent);
  server.addHandler(&ws);

  // ===== POST COMANDI =====
  // get the command input, check if JSON is right and decode the json 
  //assigning the comands to the relays
  server.on("/command", HTTP_POST,
    [](AsyncWebServerRequest *request) {},
    NULL,
    [](AsyncWebServerRequest *request,
       uint8_t *data, size_t len,
       size_t index, size_t total) {

      StaticJsonDocument<200> input;
      if (deserializeJson(input, data)) {
        request->send(400, "text/plain", "JSON errato");
        return;
      }

      if (input.containsKey("onOff")) stato["onOff"] = input["onOff"];
      if (input.containsKey("datv")) stato["datv"] = input["datv"];
      if (input.containsKey("gpsdo")) stato["gpsdo"] = input["gpsdo"];
      if (input.containsKey("fan")) stato["fan"] = input["fan"];
      if (input.containsKey("open5")) stato["open5"] = input["open5"];
      if (input.containsKey("open6")) stato["open6"] = input["open6"];


      aggiornaRelay();
      broadcastStato();
      request->send(200, "text/plain", "OK");
    });

  server.begin();
}

// ===== LOOP NON BLOCCANTE =====

// counter for defining the period
unsigned long lastRead = 0;

// continuos look - every cycletime update the system
void loop() {
  if (millis() - lastRead > cycleTime) {   
    lastRead = millis();

    Serial.println(lastRead);
    
    aggiornaIngressi();
    aggiornaRelay();
    broadcastStato();
  }
}

