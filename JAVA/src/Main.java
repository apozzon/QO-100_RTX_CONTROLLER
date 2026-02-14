/* 
 * PROGRAMMA PRINCIPALE DI GESTIONE DELLA CONNESSIONE A
 * ESP32-S3 PER IL CONTROLLO DI ALCUNE FUNZIONI (ACCESO/SPENTO, DATV, FAN ECC.)
 * E PER LA VERIFICA DEI PARAMETRI DEL RTX:
 * 	Vin = tensione ingresso
 * 	Vpower = tensione stadio finale
 * 	Apower = corrente stadio finale
 * 	Win = potenza in ingresso
 * 	Wout = potenza in uscita
 * 	Wref = potenza riflessa
 * 	Tair = temperatura aria in entrata ventilatore
 * 	Uair = umidità cs
 * 	Tpower = tewmperatura nei pressi del dissipatore del finale
 * 
 * 
 * VERSIONE 0  = utilizza solo la conversione PPM, non equalizzatori
 * 
 * Prossimi sviluppi:
 * 	Inserire equalizzatori per risposta ADC
 * 	Aggiungere parametri di configurazione ora fissi da programma;
 * 		Tempo aggiornamento (ora 2 secondi)
 * 		Colori verde-giallo-rosso dei:
 * 			SWR (verde 1.5 - giallo 2)
 * 			Tair
 * 			Uair
 * 			Tpower
 * 		
 * 
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Main {

	/**
	 * 
	 */
	
	public static String IPAddress = "192.168.0.214";

	public static boolean onOff = false;
	public static boolean datv = false;
	public static boolean relay3 = false;
	
	public static int Vin =0;
	public static int Vpower=0;
	public static int Apower =0;
	public static int mWin =0;
	public static int Wout =0;
	public static int Wref =0;
	public static int SWR =0;
	public static int Tair =0;
	public static int Uair =0;
	public static int Tpower =0;
	
	private Configuration s2;  // creo un oggetto che mi servirà per vedere se è gia attivo
	public static Config config;
	
	private JFrame frame;
	private static JTextField txtVin1;
	private JTextField txtVpower;
	private JTextField txtAPower;
	private JTextField txtMwIn;
	private JTextField txtWOut;
	private JTextField txtWRef;
	private JTextField txtSwr;
	private JTextField txtTAir;
	private JTextField txtUAir;
	private JTextField txtPower;
	private static JTextField resVin;
	private static JTextField resVpower;
	private static JTextField resApower;
	private static JTextField resmWin;
	private static JTextField resWout;
	private static JTextField resWref;
	private static JTextField resSwr;
	private static JTextField resTair;
	private static JTextField resUair;
	private static JTextField resTpower;
	private JTextField txtIP;
	private JTextField textIP;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	    
		config = Configuration.leggiConfig();
		//System.out.println("Config =" +config);
		IPAddress = config.IPAddress;
		
		// start Websoket
		new Esp32WS();
        Esp32WS.main(null);
        

	}

	
	
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	
    public static void updateValues() {
        SwingUtilities.invokeLater(() ->
        changeAll()
        );
    }
    
    public static double arrotonda(double value, int digit) {
        double temp = Math.pow(10, digit);
        return Math.round(value * temp) / temp;
     }
    
    public static void changeAll() {
    	
    	
    	// Calcolo in base ai PPM
    	double p1 = arrotonda(Vin / config.PIN1_PPM,2);
    	double p2 = arrotonda(Vpower / config.PIN2_PPM,2);
    	double p3 = arrotonda(Apower/ config.PIN3_PPM,2);
    	double p4 = arrotonda(mWin / config.PIN4_PPM,2);
    	double p5 = arrotonda(Wout/ config.PIN5_PPM,2);
    	double p6 = arrotonda(Wref/ config.PIN6_PPM,2);
    	double swr = arrotonda((1+p6/p5)/(1-p6/p5),2);
    	double p11 = arrotonda(Tair,0);
    	double p12 = arrotonda(Uair,0);
    	double p13 = arrotonda(Tpower,0);
    	
    	 resVin.setText(Double.toString(p1));
    	 resVpower.setText(Double.toString(p2));
    	 resApower.setText(Double.toString(p3));
    	 resmWin.setText(Double.toString(p4));
    	 resWout.setText(Double.toString(p5));
    	 resWref.setText(Double.toString(p6));
    	 resSwr.setText(Double.toString(swr));	 
    	 resTair.setText(Double.toString(p11));
    	 resUair.setText(Double.toString(p12));
    	 resTpower.setText(Double.toString(p13));
    	
    	 // calcolo dei colori
    	 int green = (int) (4095 * config.PIN1_GREEN);
    	 int yellow = (int) (4095 * config.PIN1_YELLOW);
    	 
    	 System.out.println("Vin="+Vin+"   p1="+p1+"   green="+green+"    yellow="+yellow);
    	 // double red = 4095 * config.PIN1_RED;
    	 if (green != 0) {			 //se config.PINx_GREEN = 0 non vengono gestiti i colori
    		 if (Vin <= green) {
    			 // campo verde
    			 resVin.setBackground(Color.green);
    		 } else {
    			 if ((Vin > green) && (Vin <= yellow)) {
    				// campo giallo
    				 resVin.setBackground(Color.yellow);
    			 } else {
    				// campo rosso
    				 resVin.setBackground(Color.red);
    			 }	 
    		 }
    	 }
    	 
    	green = (int) (4095 *4095 * config.PIN2_GREEN); 
    	yellow = (int) (4095 *4095 * config.PIN2_YELLOW);
    	 // double red = 4095 * config.PIN1_RED;
    	 if (green != 0) {			 //se config.PINx_GREEN = 0 non vengono gestiti i colori
    		 if (Vpower <= green) {
    			 // campo verde
    			 resVpower.setBackground(Color.green);
    		 } else {
    			 if (Vpower > green && Vpower < yellow) {
    				// campo giallo
    				 resVpower.setBackground(Color.yellow);
    			 } else {
    				// campo rosso
    				 resVpower.setBackground(Color.red);
    			 }	 
    		 }
    	 }
    	 
     	green = (int) (4095 *4095 * config.PIN3_GREEN);  
     	yellow = (int) (4095 *4095 * config.PIN3_YELLOW); 
     	 // double red = 4095 * config.PIN1_RED;
      	 if (green != 0) {			 //se config.PINx_GREEN = 0 non vengono gestiti i colori
    		 if (Apower <= green) {
    			 // campo verde
    			 resApower.setBackground(Color.green);
    		 } else {
    			 if (Apower > green && Apower < yellow) {
    				// campo giallo
    				 resVpower.setBackground(Color.yellow);
    			 } else {
    				// campo rosso
    				 resApower.setBackground(Color.red);
    			 }	 
    		 }
    	 }
    	 
     	green = (int) (4095 *4095 * config.PIN4_GREEN); 
     	yellow = (int) (4095 *4095 * config.PIN4_YELLOW); 
     	 // double red = 4095 * config.PIN1_RED;
     	 if (green != 0) {			 //se config.PINx_GREEN = 0 non vengono gestiti i colori
     		 if (p1 <= green) {
     			 // campo verde
     			 resmWin.setBackground(Color.green);
     		 } else {
     			 if (mWin > green && mWin < yellow) {
     				// campo giallo
     				 resmWin.setBackground(Color.yellow);
     			 } else {
     				// campo rosso
     				 resmWin.setBackground(Color.red);
     			 }	 
     		 }
     	 }
     	 
     	green = (int) (4095 *4095 * config.PIN5_GREEN); 
     	yellow = (int) (4095 *4095 * config.PIN5_YELLOW); 
     	 // double red = 4095 * config.PIN1_RED;
   	 if (green != 0) {			 //se config.PINx_GREEN = 0 non vengono gestiti i colori
		 if (Wout <= green) {
			 // campo verde
			 resWout.setBackground(Color.green);
		 } else {
			 if ((Wout > green) && (Wout <= yellow)) {
				// campo giallo
				 resWout.setBackground(Color.yellow);
			 } else {
				// campo rosso
				 resWout.setBackground(Color.red);
			 }	 
		 }
	 }
     	 
     	green = (int) (4095 *4095 * config.PIN6_GREEN); 
     	yellow = (int) (4095 *4095 * config.PIN6_YELLOW);
     	 // double red = 4095 * config.PIN1_RED;
   	 if (green != 0) {			 //se config.PINx_GREEN = 0 non vengono gestiti i colori
		 if (Wref <= green) {
			 // campo verde
			 resWref.setBackground(Color.green);
		 } else {
			 if ((Wref > green) && (Wref <= yellow)) {
				// campo giallo
				 resWref.setBackground(Color.yellow);
			 } else {
				// campo rosso
				 resWref.setBackground(Color.red);
			 }	 
		 }
	 }
     	 
  	 //SWR è fisso da programma
		 if (swr <= 1.5) {
			 // campo verde
			 resSwr.setBackground(Color.green);
		 } else {
			 if ((swr > 1.5) && (swr <= 2)) {
				// campo giallo
				 resSwr.setBackground(Color.yellow);
			 } else {
				// campo rosso
				 resSwr.setBackground(Color.red);
			 }	 
		 }
		 
	  	 //Tair è fisso da programma
			 if (Tair <= 28.0) {
				 // campo verde
				 resTair.setBackground(Color.green);
			 } else {
				 if ((Tair > 28.0) && (Tair <= 35.0)) {
					// campo giallo
					 resTair.setBackground(Color.yellow);
				 } else {
					// campo rosso
					 resTair.setBackground(Color.red);
				 }	 
			 }
			 
		  	 //Uair è fisso da programma
			 if (Uair <= 60.0) {
				 // campo verde
				 resUair.setBackground(Color.green);
			 } else {
				 if ((Uair > 60.0) && (Uair <= 80.0)) {
					// campo giallo
					 resUair.setBackground(Color.yellow);
				 } else {
					// campo rosso
					 resUair.setBackground(Color.red);
				 }	 
			 }

		  	 //Tpower è fisso da programma
			 if (Tpower <= 40.0) {
				 // campo verde
				 resTpower.setBackground(Color.green);
			 } else {
				 if ((Tpower > 40.0) && (Tpower <= 55.0)) {
					// campo giallo
					 resTpower.setBackground(Color.yellow);
				 } else {
					// campo rosso
					 resTpower.setBackground(Color.red);
				 }	 
			 }
     	 
    }
    
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Image img = new ImageIcon(this.getClass().getResource("/Parabola.png")).getImage();
		frame.setIconImage(img);
		frame.setBounds(100, 100, 652, 158);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//frame.setIconImage(logo.getImage());
		
		JButton btnOnOff = new JButton("ON/OFF");
		btnOnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Esp32Post.send("{\"onOff\": true}");
			}
		});
		btnOnOff.setBounds(115, 25, 71, 23);
		frame.getContentPane().add(btnOnOff);
		
		JTextArea txtrQoRtxController = new JTextArea();
		txtrQoRtxController.setWrapStyleWord(true);
		txtrQoRtxController.setFont(new Font("Arial Black", Font.PLAIN, 12));
		txtrQoRtxController.setText("QO-100 RTX\r\nController");
		txtrQoRtxController.setBounds(10, 0, 90, 48);
		frame.getContentPane().add(txtrQoRtxController);
		
		JButton btnDatv = new JButton("DATV");
		btnDatv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Esp32Post.send("{\"datv\": true}");
			}
		});
		btnDatv.setBounds(196, 25, 71, 23);
		frame.getContentPane().add(btnDatv);
		
		JButton btnGpsdo = new JButton("GPSDO");
		btnGpsdo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Esp32Post.send("{\"relay3\":true}");
			}
		});
		btnGpsdo.setBounds(277, 25, 71, 23);
		frame.getContentPane().add(btnGpsdo);
		
		JButton btnFan = new JButton("FAN");
		btnFan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Esp32Post.send("{\"onOff\": false, \"datv\": false, \"relay3\":false}");
			}
		});
		btnFan.setBounds(362, 25, 71, 23);
		frame.getContentPane().add(btnFan);
		
		txtVin1 = new JTextField();
		txtVin1.setText("Vin");
		txtVin1.setHorizontalAlignment(SwingConstants.CENTER);
		txtVin1.setColumns(10);
		txtVin1.setBounds(10, 59, 52, 23);
		frame.getContentPane().add(txtVin1);
		
		txtVpower = new JTextField();
		txtVpower.setText("V power");
		txtVpower.setHorizontalAlignment(SwingConstants.CENTER);
		txtVpower.setColumns(10);
		txtVpower.setBounds(72, 59, 52, 23);
		frame.getContentPane().add(txtVpower);
		
		txtAPower = new JTextField();
		txtAPower.setText("A power");
		txtAPower.setHorizontalAlignment(SwingConstants.CENTER);
		txtAPower.setColumns(10);
		txtAPower.setBounds(134, 59, 52, 23);
		frame.getContentPane().add(txtAPower);
		
		txtMwIn = new JTextField();
		txtMwIn.setText("mW in");
		txtMwIn.setHorizontalAlignment(SwingConstants.CENTER);
		txtMwIn.setColumns(10);
		txtMwIn.setBounds(196, 59, 52, 23);
		frame.getContentPane().add(txtMwIn);
		
		txtWOut = new JTextField();
		txtWOut.setText("W out");
		txtWOut.setHorizontalAlignment(SwingConstants.CENTER);
		txtWOut.setColumns(10);
		txtWOut.setBounds(257, 59, 52, 23);
		frame.getContentPane().add(txtWOut);
		
		txtWRef = new JTextField();
		txtWRef.setText("W ref");
		txtWRef.setHorizontalAlignment(SwingConstants.CENTER);
		txtWRef.setColumns(10);
		txtWRef.setBounds(319, 59, 52, 23);
		frame.getContentPane().add(txtWRef);
		
		txtSwr = new JTextField();
		txtSwr.setText("SWR");
		txtSwr.setHorizontalAlignment(SwingConstants.CENTER);
		txtSwr.setColumns(10);
		txtSwr.setBounds(381, 59, 52, 23);
		frame.getContentPane().add(txtSwr);
		
		txtTAir = new JTextField();
		txtTAir.setText("T air");
		txtTAir.setHorizontalAlignment(SwingConstants.CENTER);
		txtTAir.setColumns(10);
		txtTAir.setBounds(443, 59, 52, 23);
		frame.getContentPane().add(txtTAir);
		
		txtUAir = new JTextField();
		txtUAir.setText("U% air");
		txtUAir.setHorizontalAlignment(SwingConstants.CENTER);
		txtUAir.setColumns(10);
		txtUAir.setBounds(503, 59, 52, 23);
		frame.getContentPane().add(txtUAir);
		
		txtPower = new JTextField();
		txtPower.setText("T power");
		txtPower.setHorizontalAlignment(SwingConstants.CENTER);
		txtPower.setColumns(10);
		txtPower.setBounds(565, 59, 52, 23);
		frame.getContentPane().add(txtPower);
		
		resVin = new JTextField();
		resVin.setText("0");
		resVin.setHorizontalAlignment(SwingConstants.CENTER);
		resVin.setColumns(10);
		resVin.setBounds(10, 88, 52, 23);
		frame.getContentPane().add(resVin);
		
		resVpower = new JTextField();
		resVpower.setText("0");
		resVpower.setHorizontalAlignment(SwingConstants.CENTER);
		resVpower.setColumns(10);
		resVpower.setBounds(72, 88, 52, 23);
		frame.getContentPane().add(resVpower);
		
		resApower = new JTextField();
		resApower.setText("0");
		resApower.setHorizontalAlignment(SwingConstants.CENTER);
		resApower.setColumns(10);
		resApower.setBounds(134, 88, 52, 23);
		frame.getContentPane().add(resApower);
		
		resmWin = new JTextField();
		resmWin.setText("0");
		resmWin.setHorizontalAlignment(SwingConstants.CENTER);
		resmWin.setColumns(10);
		resmWin.setBounds(196, 88, 52, 23);
		frame.getContentPane().add(resmWin);
		
		resWout = new JTextField();
		resWout.setText("0");
		resWout.setHorizontalAlignment(SwingConstants.CENTER);
		resWout.setColumns(10);
		resWout.setBounds(257, 88, 52, 23);
		frame.getContentPane().add(resWout);
		
		resWref = new JTextField();
		resWref.setText("0");
		resWref.setHorizontalAlignment(SwingConstants.CENTER);
		resWref.setColumns(10);
		resWref.setBounds(319, 88, 52, 23);
		frame.getContentPane().add(resWref);
		
		resSwr = new JTextField();
		resSwr.setText("0");
		resSwr.setHorizontalAlignment(SwingConstants.CENTER);
		resSwr.setColumns(10);
		resSwr.setBounds(381, 88, 52, 23);
		frame.getContentPane().add(resSwr);
		
		resTair = new JTextField();
		resTair.setText("0");
		resTair.setHorizontalAlignment(SwingConstants.CENTER);
		resTair.setColumns(10);
		resTair.setBounds(443, 88, 52, 23);
		frame.getContentPane().add(resTair);
		
		resUair = new JTextField();
		resUair.setText("0");
		resUair.setHorizontalAlignment(SwingConstants.CENTER);
		resUair.setColumns(10);
		resUair.setBounds(503, 88, 52, 23);
		frame.getContentPane().add(resUair);
		
		resTpower = new JTextField();
		resTpower.setText("0");
		resTpower.setHorizontalAlignment(SwingConstants.CENTER);
		resTpower.setColumns(10);
		resTpower.setBounds(565, 88, 52, 23);
		frame.getContentPane().add(resTpower);
		
		JButton btnOpen1 = new JButton("open");
		btnOpen1.setBounds(443, 25, 71, 23);
		frame.getContentPane().add(btnOpen1);
		
		JButton btnOpen2 = new JButton("open");
		btnOpen2.setBounds(521, 25, 71, 23);
		frame.getContentPane().add(btnOpen2);
		
		JButton btnSetting = new JButton("...");
		
		 btnSetting.addActionListener(e -> {

	            if (s2 == null || !s2.isDisplayable()) {
	                // se non esiste o è stata chiusa
	                s2 = new Configuration();
	                s2.setVisible(true);
	            } else {
	                // se è già aperta la porto in primo piano
	                s2.toFront();
	                s2.requestFocus();
	            }

	        });
		 btnSetting.setBounds(601, 25, 25, 23);
		frame.getContentPane().add(btnSetting);
		
		txtIP = new JTextField();
		txtIP.setText("IP ESP32 ->");
		txtIP.setHorizontalAlignment(SwingConstants.CENTER);
		txtIP.setColumns(10);
		txtIP.setBounds(115, 2, 71, 22);
		frame.getContentPane().add(txtIP);
		
		textIP = new JTextField();
		textIP.setText(IPAddress);
		textIP.setBounds(195, 4, 114, 20);
		frame.getContentPane().add(textIP);
		textIP.setColumns(10);
		
	}
	
	
}
