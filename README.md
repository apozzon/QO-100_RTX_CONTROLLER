# QO-100_RTX_CONTROLLER
QO-100 RTX CONTROLLER
ESP32 CONTROLLER PER QO100
 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 
 	QO-100 RTX		IP		SSID		PASSW	 
 	Controller		192.168.0.214		SKYAP2		************	 
 																 
 	ON/OFF		DATV		GPSDO		FAN		…		…		…		…	 
 																 
 																 
 	V in		V pow		A pow		mW in		W out		W ref		SWR		T air-pow	 
 	13.8		27.5		1.5		10		7,5		0,6		1,788789		19-33	 
 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 	 




Misure
Parametro	Max	PIN	Note
V ingresso	13.8 V	1	Partitore 1:10
V potenza	30V	2	Partitore 1:10
A potenza	10A	3	Parallelo a R 0,1 ohm da 5 W (10A = 1V)
W power in 	5V	4	
W power out	5V	5	
W reflected	5V	6	
C temperatura	60C	11	Apposito modulo digitale
			

Controlli
Parametro	Stato	PIN	Note
On / Off	0-1	7	Accende e spegne SDR – Arduino sempre on low
DATV	0-1	8	Commuta 12V-18V su Bias-T
Heat	0-1	9	Accende resistenza riscaldamento clima freddo

INSTALLAZIONE IDE E SETTAGGIO BOARD
Setup Steps for Arduino IDE
1.	Open Arduino IDE and go to File > Preferences.
2.	Add Board Manager URL: Paste the following into the "Additional Boards Manager URLs" field:
https://raw.githubusercontent.com/espressif/arduino-esp32/gh-pages/package_esp32_index.json.
3.	Install Boards: Go to Tools > Board > Boards Manager, search for "esp32" and install the latest version by Espressif Systems.
 
NB: se non carica support mettere queste due istruzioni in C:\Users\Andrea.DESKTOP-IK5K4JJ\.arduinoIDE\ arduino-cli.yaml
network:
  connection_timeout: 300s

4.	Select Board: Select Tools > Board > ESP32 Arduino > ESP32S3 Dev Module.
5.	Configure Tools Menu (Crucial for N16R8):
1.	USB CDC On Boot: "Enabled" (Allows Serial.print via USB).
2.	Flash Size: "16MB (128Mb)".
3.	Flash Mode: "QIO 80MHz" or "QOIO 80MHz".
4.	Partition Scheme: "16M Flash (3M APP/9.9M FATFS)" or similar large partition.
5.	PSRAM: "OPI PSRAM".
6.	Upload: Connect the board, select the correct Port, and upload a test sketch. 



ARDUINO
Additional board manager URL: https://espressif.github.io/arduino-esp32/package_esp32_index.json
Board scelta : ESP32S3 Dev Module

Librerie
https://github.com/ESP32Async/AsyncTCP
https://github.com/ESP32Async/ESPAsyncWebServer
DHT kxn

Java aggiunta libreria Google json
tasto destro su Progetto – Build Path -Add External Archives) – Scegliere file scaricato (es.  gson-2.3.1.jar)

