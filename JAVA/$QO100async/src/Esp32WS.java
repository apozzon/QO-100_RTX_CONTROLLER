import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;


public class Esp32WS implements WebSocket.Listener {
	
	// definizione del file JSON da creare. In arrivo da ESP32 è:
	//{"onOff":false,"datv":false,"gpsdo":true,"fan":false,"open5":false,"open6":false,"Vin":0,"Vpower":0,"Apower":0,"Win":0,"Wout":0,"Wref":0,"Tair":24.1,"Uair":44,"Tpower":0}
	
	class Config {
	    boolean onOff;
	    boolean datv;
	    boolean gpsdo;
	    boolean fan;
	    boolean open5;
	    boolean open6;
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
        Main.gpsdo = cfg.gpsdo;
        Main.fan = cfg.fan;
        Main.open5 = cfg.open5;
        Main.open6 = cfg.open6;
        Main.Vin = cfg.Vin;
	    Main.Vpower = cfg.Vpower;
	    Main.Apower = cfg.Apower;
	    Main.mWin = cfg.Win;
	    Main.Wout = cfg.Wout;
	    Main.Wref = cfg.Wref;
	    Main.Tair = (int)cfg.Tair;
	    Main.Uair = (int)cfg.Uair;
	    Main.Tpower = (int)cfg.Tpower;
	    Main.onOff = cfg.onOff;
	    Main.datv = cfg.datv;
	    Main.gpsdo = cfg.gpsdo;
	    Main.fan= cfg.fan;
	    Main.open5 = cfg.open5;
	    Main.open6 = cfg.open6;

        // update values on the screen in main
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

