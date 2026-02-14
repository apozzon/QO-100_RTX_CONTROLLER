#include <WiFi.h>
#include <AsyncTCP.h>
#include <ESPAsyncWebServer.h>
#include <ArduinoJson.h>
#include <DHT.h>

#define DHTTYPE DHT22

const int cycleTime = 2000;  // in millisecondi
const char* ssid = "SKYAP2";
const char* password = "aaabbbcccddd";

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
const int RELAY3 = 9;


const int DHT1_PIN = 10;
const int DHT2_PIN = 11;

DHT dht1(DHT1_PIN, DHTTYPE);
DHT dht2(DHT2_PIN, DHTTYPE);

// ===== STATO =====
StaticJsonDocument<512> stato;

// ===== UPDATE HARDWARE =====
void aggiornaRelay() {
  digitalWrite(ONOFF, stato["onOff"] ? HIGH : LOW);
  digitalWrite(DATV, stato["datv"] ? HIGH : LOW);
  digitalWrite(RELAY3, stato["relay3"] ? HIGH : LOW);
}

void aggiornaIngressi() {

stato["Vin"] = analogRead(Vin);
stato["Vpower"] = analogRead(Vpower);
stato["Apower"] = analogRead(Apower);
stato["Win"] = analogRead(Win);
stato["Wout"] = analogRead(Wout);
stato["Wref"] = analogRead(Wref);


  float t1 = dht1.readTemperature();
  float h1 = dht1.readHumidity();
  float t2 = dht2.readTemperature();
  float h2 = dht2.readHumidity();


  if (!isnan(t1) && !isnan(h1)) {
    //stato["dht10"]["t"] = t1;
    //stato["dht10"]["h"] = h1;
    stato["Tair"] = t1;
    stato["Uair"] = h1;

  }
  if (!isnan(t2) && !isnan(h2)) {
    //stato["dht11"]["t"] = t2;
    //stato["dht11"]["h"] = h2;
    stato["Tpower"] = t2;
 }
}

// ===== PUSH STATO =====
void broadcastStato() {
  String json;
  serializeJson(stato, json);
  ws.textAll(json);
  Serial.println(json);
}

// ===== WS EVENTS =====
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

// ===== SETUP =====
void setup() {
  Serial.begin(115200);

  pinMode(ONOFF, OUTPUT);
  pinMode(DATV, OUTPUT);
  pinMode(RELAY3, OUTPUT);

  dht1.begin();
  dht2.begin();

  stato["onOff"] = false;
  stato["datv"] = false;
  stato["relay3"] = false;

  aggiornaRelay();

  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) delay(500);
  
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());


  ws.onEvent(onWsEvent);
  server.addHandler(&ws);

  // ===== POST COMANDI =====
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
      if (input.containsKey("relay3")) stato["relay3"] = input["relay3"];

      aggiornaRelay();
      broadcastStato();
      request->send(200, "text/plain", "OK");
    });

  server.begin();
}

// ===== LOOP NON BLOCCANTE =====
unsigned long lastRead = 0;

void loop() {
  if (millis() - lastRead > cycleTime) {   
    lastRead = millis();

    Serial.println(lastRead);
    
    aggiornaIngressi();
    broadcastStato();
  }
}
