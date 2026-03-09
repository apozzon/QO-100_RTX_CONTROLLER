

/* 	Posta i comandi di accensione, datv, fan ecc. tramite un 
 	POST sul Winsocket ricevuti tramite il file JSON composto in Main
*/

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Esp32Post {

    public static void send(String json) {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create("http://"+Main.IPAddress+"/command"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpClient.newHttpClient()
            .sendAsync(req, HttpResponse.BodyHandlers.ofString())
            .thenAccept(r -> System.out.println("POST OK"));
    }
}
