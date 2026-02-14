import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;


public class Esp32WS implements WebSocket.Listener {
	
	// definizione del file JSON da creare. In arrivo da ESP32 è:
	//{"onOff":false,"datv":false,"relay3":false,"Vin":486,"Vpower":467,"Apower":613,"Win":438,"Wout":201,"Wref":151,"Tair":25,"Uair":43.1,"Tpower":50.0}
	
	class Config {
	    boolean onOff;
	    boolean datv;
	    boolean relay3;
	    int Vin;
	    int Vpower;
	    int Apower;
	    int Win;
	    int Wout;
	    int Wref;
	    double Tair;
	    double Uair;
	    double Tpower;  
	}
	

    @Override
    public void onOpen(WebSocket ws) {
        //System.out.println("WebSocket connesso");
        ws.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket ws, CharSequence data, boolean last) {
        System.out.println("Stato ricevuto: " + data);
        String jsonIn = String.valueOf(data);
        Gson gson = new Gson();
        Config cfg = gson.fromJson(jsonIn, Config.class);

        Main.onOff = cfg.onOff;
        Main.datv = cfg.datv;
        Main.relay3 = cfg.relay3;
        Main.Vin = cfg.Vin;
	    Main.Vpower = cfg.Vpower;
	    Main.Apower = cfg.Apower;
	    Main.mWin = cfg.Win;
	    Main.Wout = cfg.Wout;
	    Main.Wref = cfg.Wref;
	    Main.Tair = (int)cfg.Tair;
	    Main.Uair = (int)cfg.Uair;
	    Main.Tpower = (int)cfg.Tpower;

        // aggiorno i valori nelle labels in Main
	    Main.updateValues();
        
        ws.request(1);

        return null;
    }

    public static void main(String[] args) {
    	
        HttpClient.newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(URI.create("ws://"+Main.IPAddress+"/ws"), new Esp32WS());
        try { Thread.sleep(99999); } catch (Exception ignored) {}
    }

    
}

