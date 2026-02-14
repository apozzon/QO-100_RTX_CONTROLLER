
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
//ciao
class Config {
	String IPAddress;
	boolean OnOff;
	boolean Datv;
	boolean GPSDO;
	boolean Fan;
	boolean Relay3;
	double PIN1_R0;
	double PIN1_Rc;
	double PIN1_PPM;
	double PIN1_E00;
	double PIN1_E10;
	double PIN1_E20;
	double PIN1_E30;
	double PIN1_E40;
	double PIN1_E50;
	double PIN1_E60;
	double PIN1_E70;
	double PIN1_E80;
	double PIN1_E90;
	double PIN1_GREEN;
	double PIN1_YELLOW;
	double PIN1_RED;
	double PIN2_R0;
	double PIN2_Rc;
	double PIN2_PPM;
	double PIN2_E00;
	double PIN2_E10;
	double PIN2_E20;
	double PIN2_E30;
	double PIN2_E40;
	double PIN2_E50;
	double PIN2_E60;
	double PIN2_E70;
	double PIN2_E80;
	double PIN2_E90;
	double PIN2_GREEN;
	double PIN2_YELLOW;
	double PIN2_RED;
	double PIN3_R0;
	double PIN3_Rc;
	double PIN3_PPM;
	double PIN3_E00;
	double PIN3_E10;
	double PIN3_E20;
	double PIN3_E30;
	double PIN3_E40;
	double PIN3_E50;
	double PIN3_E60;
	double PIN3_E70;
	double PIN3_E80;
	double PIN3_E90;
	double PIN3_GREEN;
	double PIN3_YELLOW;
	double PIN3_RED;
	double PIN4_R0;
	double PIN4_Rc;
	double PIN4_PPM;
	double PIN4_E00;
	double PIN4_E10;
	double PIN4_E20;
	double PIN4_E30;
	double PIN4_E40;
	double PIN4_E50;
	double PIN4_E60;
	double PIN4_E70;
	double PIN4_E80;
	double PIN4_E90;
	double PIN4_GREEN;
	double PIN4_YELLOW;
	double PIN4_RED;
	double PIN5_R0;
	double PIN5_Rc;
	double PIN5_PPM;
	double PIN5_E00;
	double PIN5_E10;
	double PIN5_E20;
	double PIN5_E30;
	double PIN5_E40;
	double PIN5_E50;
	double PIN5_E60;
	double PIN5_E70;
	double PIN5_E80;
	double PIN5_E90;
	double PIN5_GREEN;
	double PIN5_YELLOW;
	double PIN5_RED;
	double PIN6_R0;
	double PIN6_Rc;
	double PIN6_PPM;
	double PIN6_E00;
	double PIN6_E10;
	double PIN6_E20;
	double PIN6_E30;
	double PIN6_E40;
	double PIN6_E50;
	double PIN6_E60;
	double PIN6_E70;
	double PIN6_E80;
	double PIN6_E90;
	double PIN6_GREEN;
	double PIN6_YELLOW;
	double PIN6_RED;
}

public class Configuration extends JFrame {
	private JTable table;
	private DefaultTableModel model;
	private Config config;
	private JTextField txtIPAddress;
	

	public Configuration() {
	    setTitle("Edit your parameters");
	    setSize(600, 400);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setLayout(new BorderLayout());

	    config = leggiConfig();   // <-- legge JSON
	    Main.config = config;
	    creaPannelloIP();  
	    creaTabella();
	    creaPulsanteSalva();
	}

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH = "C:/Users/Andrea.DESKTOP-IK5K4JJ/AppData/Roaming/QO-100async/config.json/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    

    // Legge il file JSON
    public static Config leggiConfig() {

        try (FileReader reader = new FileReader(FILE_PATH)) {
        	//System.out.println(" gson letto in leggiConfig  "+gson.fromJson(reader, Config.class));
        	return gson.fromJson(reader, Config.class);
        } catch (IOException e) {
            System.out.println("File non trovato, creo configurazione default");
            return creaDefault();
        }
    }


 
    
    // Scrive il file JSON
    public static void scriviConfig(Config config) {

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(config, writer);
            Main.config = config;
            System.out.println("Configurazione salvata");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Configurazione iniziale
    private static Config creaDefault() {
        Config config = new Config();
        config.IPAddress = "192.168.0.214";
        config.OnOff = false;
        config.Datv = false;
        config.GPSDO = false;
        config.Fan = false;
        config.Relay3 = false;
        config.PIN1_R0 = 20000;
        config.PIN1_Rc = 120000;
        config.PIN1_PPM = 1;
        config.PIN1_E00 = 1;
        config.PIN1_E10 = 1;
        config.PIN1_E20 = 1;
        config.PIN1_E30 = 1;
        config.PIN1_E40 = 1;
        config.PIN1_E50 = 1;
        config.PIN1_E60 = 1;
        config.PIN1_E70 = 1;
        config.PIN1_E80 = 1;
        config.PIN1_E90 = 1;
        config.PIN1_GREEN = 0;
        config.PIN1_YELLOW = 0;
        config.PIN1_RED = 0;
        config.PIN2_R0 = 10000;
        config.PIN2_Rc = 120000;
        config.PIN2_PPM = 1;
        config.PIN2_E00 = 1;
        config.PIN2_E10 = 1;
        config.PIN2_E20 = 1;
        config.PIN2_E30 = 1;
        config.PIN2_E40 = 1;
        config.PIN2_E50 = 1;
        config.PIN2_E60 = 1;
        config.PIN2_E70 = 1;
        config.PIN2_E80 = 1;
        config.PIN2_E90 = 1;
        config.PIN2_GREEN = 0;
        config.PIN2_YELLOW = 0;
        config.PIN2_RED = 0;
        config.PIN3_R0 = 20000;
        config.PIN3_Rc = 120000;
        config.PIN3_PPM = 1;
        config.PIN3_E00 = 1;
        config.PIN3_E10 = 1;
        config.PIN3_E20 = 1;
        config.PIN3_E30 = 1;
        config.PIN3_E40 = 1;
        config.PIN3_E50 = 1;
        config.PIN3_E60 = 1;
        config.PIN3_E70 = 1;
        config.PIN3_E80 = 1;
        config.PIN3_E90 = 1;
        config.PIN3_GREEN = 0;
        config.PIN3_YELLOW = 0;
        config.PIN3_RED = 0;
        config.PIN4_R0 = 20000;
        config.PIN4_Rc = 120000;
        config.PIN4_PPM = 1;
        config.PIN4_E00 = 1;
        config.PIN4_E10 = 1;
        config.PIN4_E20 = 1;
        config.PIN4_E30 = 1;
        config.PIN4_E40 = 1;
        config.PIN4_E50 = 1;
        config.PIN4_E60 = 1;
        config.PIN4_E70 = 1;
        config.PIN4_E80 = 1;
        config.PIN4_E90 = 1;
        config.PIN4_GREEN = 0;
        config.PIN4_YELLOW = 0;
        config.PIN4_RED = 0;
        config.PIN5_R0 = 20000;
        config.PIN5_Rc = 120000;
        config.PIN5_PPM = 1;
        config.PIN5_E00 = 1;
        config.PIN5_E10 = 1;
        config.PIN5_E20 = 1;
        config.PIN5_E30 = 1;
        config.PIN5_E40 = 1;
        config.PIN5_E50 = 1;
        config.PIN5_E60 = 1;
        config.PIN5_E70 = 1;
        config.PIN5_E80 = 1;
        config.PIN5_E90 = 1;
        config.PIN5_GREEN = 0;
        config.PIN5_YELLOW = 0;
        config.PIN5_RED = 0;
        config.PIN6_R0 = 20000;
        config.PIN6_Rc = 120000;
        config.PIN6_PPM = 1;
        config.PIN6_E00 = 1;
        config.PIN6_E10 = 1;
        config.PIN6_E20 = 1;
        config.PIN6_E30 = 1;
        config.PIN6_E40 = 1;
        config.PIN6_E50 = 1;
        config.PIN6_E60 = 1;
        config.PIN6_E70 = 1;
        config.PIN6_E80 = 1;
        config.PIN6_E90 = 1;
        config.PIN6_GREEN = 0;
        config.PIN6_YELLOW = 0;
        config.PIN6_RED = 0;

        scriviConfig(config);
 
        return config;
    }
    
	public void creaTabella() {
        // 1. Dati della tabella (righe e colonne)
        String[] columnNames = {"Measure","PIN1", "PIN2", "PIN3", "PIN4","PIN5", "PIN6"};
        Object[][] data = {
        		{"R0",config.PIN1_R0,config.PIN2_R0,config.PIN3_R0,config.PIN4_R0,config.PIN5_R0,config.PIN6_R0},
        		{"Rc",config.PIN1_Rc,config.PIN2_Rc,config.PIN3_Rc,config.PIN4_Rc,config.PIN5_Rc,config.PIN6_Rc},
        		{"PPM",config.PIN1_PPM,config.PIN2_PPM,config.PIN3_PPM,config.PIN4_PPM,config.PIN5_PPM,config.PIN6_PPM},
        		{"E00",config.PIN1_E00,config.PIN2_E00,config.PIN3_E00,config.PIN4_E00,config.PIN5_E00,config.PIN6_E00},
        		{"E10",config.PIN1_E10,config.PIN2_E10,config.PIN3_E10,config.PIN4_E10,config.PIN5_E10,config.PIN6_E10},
        		{"E20",config.PIN1_E20,config.PIN2_E20,config.PIN3_E20,config.PIN4_E20,config.PIN5_E20,config.PIN6_E20},
        		{"E30",config.PIN1_E30,config.PIN2_E30,config.PIN3_E30,config.PIN4_E30,config.PIN5_E30,config.PIN6_E30},
        		{"E40",config.PIN1_E40,config.PIN2_E40,config.PIN3_E40,config.PIN4_E40,config.PIN5_E40,config.PIN6_E40},
        		{"E50",config.PIN1_E50,config.PIN2_E50,config.PIN3_E50,config.PIN4_E50,config.PIN5_E50,config.PIN6_E50},
        		{"E60",config.PIN1_E60,config.PIN2_E60,config.PIN3_E60,config.PIN4_E60,config.PIN5_E60,config.PIN6_E60},
        		{"E70",config.PIN1_E70,config.PIN2_E70,config.PIN3_E70,config.PIN4_E70,config.PIN5_E70,config.PIN6_E70},
        		{"E80",config.PIN1_E80,config.PIN2_E80,config.PIN3_E80,config.PIN4_E80,config.PIN5_E80,config.PIN6_E80},
        		{"E90",config.PIN1_E90,config.PIN2_E90,config.PIN3_E90,config.PIN4_E90,config.PIN5_E90,config.PIN6_E90},
        		{"GREEN",config.PIN1_GREEN,config.PIN2_GREEN,config.PIN3_GREEN,config.PIN4_GREEN,config.PIN5_GREEN,config.PIN6_GREEN},
        		{"YELLOW",config.PIN1_YELLOW,config.PIN2_YELLOW,config.PIN3_YELLOW,config.PIN4_YELLOW,config.PIN5_YELLOW,config.PIN6_YELLOW},
        		{"RED",config.PIN1_RED,config.PIN2_RED,config.PIN3_RED,config.PIN4_RED,config.PIN5_RED,config.PIN6_RED},

        };

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	private void salvaSuJson() {
		
	    int scelta = javax.swing.JOptionPane.showConfirmDialog(
	            this,
	            "Sei sicuro di voler salvare la configurazione?\nNOTA BENE : se cambi IP devi far ripartire il programma perché \nWinsocket è già attivo col vecchio IP.",
	            "\nConferma salvataggio",
	            javax.swing.JOptionPane.YES_NO_OPTION,
	            javax.swing.JOptionPane.QUESTION_MESSAGE
	    );

	    if (scelta != javax.swing.JOptionPane.YES_OPTION) {
	        return; // esce se l'utente preme NO
	    }

	    try {
	    	config.IPAddress = txtIPAddress.getText();  
	    	
	        config.PIN1_R0 = Double.parseDouble(model.getValueAt(0,1).toString());
	        config.PIN2_R0 = Double.parseDouble(model.getValueAt(0,2).toString());
	        config.PIN3_R0 = Double.parseDouble(model.getValueAt(0,3).toString());
	        config.PIN4_R0 = Double.parseDouble(model.getValueAt(0,4).toString());
	        config.PIN5_R0 = Double.parseDouble(model.getValueAt(0,5).toString());
	        config.PIN6_R0 = Double.parseDouble(model.getValueAt(0,6).toString());

	        config.PIN1_Rc = Double.parseDouble(model.getValueAt(1,1).toString());
	        config.PIN2_Rc = Double.parseDouble(model.getValueAt(1,2).toString());
	        config.PIN3_Rc = Double.parseDouble(model.getValueAt(1,3).toString());
	        config.PIN4_Rc = Double.parseDouble(model.getValueAt(1,4).toString());
	        config.PIN5_Rc = Double.parseDouble(model.getValueAt(1,5).toString());
	        config.PIN6_Rc = Double.parseDouble(model.getValueAt(1,6).toString());
	        
	        config.PIN1_PPM = Double.parseDouble(model.getValueAt(2,1).toString());
	        config.PIN2_PPM = Double.parseDouble(model.getValueAt(2,2).toString());
	        config.PIN3_PPM = Double.parseDouble(model.getValueAt(2,3).toString());
	        config.PIN4_PPM = Double.parseDouble(model.getValueAt(2,4).toString());
	        config.PIN5_PPM = Double.parseDouble(model.getValueAt(2,5).toString());
	        config.PIN6_PPM = Double.parseDouble(model.getValueAt(2,6).toString());
	        
	        config.PIN1_E00 = Double.parseDouble(model.getValueAt(3,1).toString());
	        config.PIN2_E00 = Double.parseDouble(model.getValueAt(3,2).toString());
	        config.PIN3_E00 = Double.parseDouble(model.getValueAt(3,3).toString());
	        config.PIN4_E00 = Double.parseDouble(model.getValueAt(3,4).toString());
	        config.PIN5_E00 = Double.parseDouble(model.getValueAt(3,5).toString());
	        config.PIN6_E00 = Double.parseDouble(model.getValueAt(3,6).toString());        
	        
	        config.PIN1_E10 = Double.parseDouble(model.getValueAt(4,1).toString());
	        config.PIN2_E10 = Double.parseDouble(model.getValueAt(4,2).toString());
	        config.PIN3_E10 = Double.parseDouble(model.getValueAt(4,3).toString());
	        config.PIN4_E10 = Double.parseDouble(model.getValueAt(4,4).toString());
	        config.PIN5_E10 = Double.parseDouble(model.getValueAt(4,5).toString());
	        config.PIN6_E10 = Double.parseDouble(model.getValueAt(4,6).toString());
	        
	        config.PIN1_E20 = Double.parseDouble(model.getValueAt(5,1).toString());
	        config.PIN2_E20 = Double.parseDouble(model.getValueAt(5,2).toString());
	        config.PIN3_E20 = Double.parseDouble(model.getValueAt(5,3).toString());
	        config.PIN4_E20 = Double.parseDouble(model.getValueAt(5,4).toString());
	        config.PIN5_E20 = Double.parseDouble(model.getValueAt(5,5).toString());
	        config.PIN6_E20 = Double.parseDouble(model.getValueAt(5,6).toString());
	        
	        config.PIN1_E30 = Double.parseDouble(model.getValueAt(6,1).toString());
	        config.PIN2_E30 = Double.parseDouble(model.getValueAt(6,2).toString());
	        config.PIN3_E30 = Double.parseDouble(model.getValueAt(6,3).toString());
	        config.PIN4_E30 = Double.parseDouble(model.getValueAt(6,4).toString());
	        config.PIN5_E30 = Double.parseDouble(model.getValueAt(6,5).toString());
	        config.PIN6_E30 = Double.parseDouble(model.getValueAt(6,6).toString());
	        
	        config.PIN1_E40 = Double.parseDouble(model.getValueAt(7,1).toString());
	        config.PIN2_E40 = Double.parseDouble(model.getValueAt(7,2).toString());
	        config.PIN3_E40 = Double.parseDouble(model.getValueAt(7,3).toString());
	        config.PIN4_E40 = Double.parseDouble(model.getValueAt(7,4).toString());
	        config.PIN5_E40 = Double.parseDouble(model.getValueAt(7,5).toString());
	        config.PIN6_E40 = Double.parseDouble(model.getValueAt(7,6).toString());
	        
	        config.PIN1_E50 = Double.parseDouble(model.getValueAt(8,1).toString());
	        config.PIN2_E50 = Double.parseDouble(model.getValueAt(8,2).toString());
	        config.PIN3_E50 = Double.parseDouble(model.getValueAt(8,3).toString());
	        config.PIN4_E50 = Double.parseDouble(model.getValueAt(8,4).toString());
	        config.PIN5_E50 = Double.parseDouble(model.getValueAt(8,5).toString());
	        config.PIN6_E50 = Double.parseDouble(model.getValueAt(8,6).toString());
	        
	        config.PIN1_E60 = Double.parseDouble(model.getValueAt(9,1).toString());
	        config.PIN2_E60 = Double.parseDouble(model.getValueAt(9,2).toString());
	        config.PIN3_E60 = Double.parseDouble(model.getValueAt(9,3).toString());
	        config.PIN4_E60 = Double.parseDouble(model.getValueAt(9,4).toString());
	        config.PIN5_E60 = Double.parseDouble(model.getValueAt(9,5).toString());
	        config.PIN6_E60 = Double.parseDouble(model.getValueAt(9,6).toString());
	        
	        config.PIN1_E70 = Double.parseDouble(model.getValueAt(10,1).toString());
	        config.PIN2_E70 = Double.parseDouble(model.getValueAt(10,2).toString());
	        config.PIN3_E70 = Double.parseDouble(model.getValueAt(10,3).toString());
	        config.PIN4_E70 = Double.parseDouble(model.getValueAt(10,4).toString());
	        config.PIN5_E70 = Double.parseDouble(model.getValueAt(10,5).toString());
	        config.PIN6_E70 = Double.parseDouble(model.getValueAt(10,6).toString());
	        
	        config.PIN1_E80 = Double.parseDouble(model.getValueAt(11,1).toString());
	        config.PIN2_E80 = Double.parseDouble(model.getValueAt(11,2).toString());
	        config.PIN3_E80 = Double.parseDouble(model.getValueAt(11,3).toString());
	        config.PIN4_E80 = Double.parseDouble(model.getValueAt(11,4).toString());
	        config.PIN5_E80 = Double.parseDouble(model.getValueAt(11,5).toString());
	        config.PIN6_E80 = Double.parseDouble(model.getValueAt(11,6).toString());
	        
	        config.PIN1_E90 = Double.parseDouble(model.getValueAt(12,1).toString());
	        config.PIN2_E90 = Double.parseDouble(model.getValueAt(12,2).toString());
	        config.PIN3_E90 = Double.parseDouble(model.getValueAt(12,3).toString());
	        config.PIN4_E90 = Double.parseDouble(model.getValueAt(12,4).toString());
	        config.PIN5_E90 = Double.parseDouble(model.getValueAt(12,5).toString());
	        config.PIN6_E90 = Double.parseDouble(model.getValueAt(12,6).toString());

	        config.PIN1_GREEN = Double.parseDouble(model.getValueAt(13,1).toString());
	        config.PIN2_GREEN = Double.parseDouble(model.getValueAt(13,2).toString());
	        config.PIN3_GREEN = Double.parseDouble(model.getValueAt(13,3).toString());
	        config.PIN4_GREEN = Double.parseDouble(model.getValueAt(13,4).toString());
	        config.PIN5_GREEN = Double.parseDouble(model.getValueAt(13,5).toString());
	        config.PIN6_GREEN = Double.parseDouble(model.getValueAt(13,6).toString());
	        
	        config.PIN1_YELLOW = Double.parseDouble(model.getValueAt(14,1).toString());
	        config.PIN2_YELLOW = Double.parseDouble(model.getValueAt(14,2).toString());
	        config.PIN3_YELLOW = Double.parseDouble(model.getValueAt(14,3).toString());
	        config.PIN4_YELLOW = Double.parseDouble(model.getValueAt(14,4).toString());
	        config.PIN5_YELLOW = Double.parseDouble(model.getValueAt(14,5).toString());
	        config.PIN6_YELLOW = Double.parseDouble(model.getValueAt(14,6).toString());
	        
	        config.PIN1_RED = Double.parseDouble(model.getValueAt(15,1).toString());
	        config.PIN2_RED = Double.parseDouble(model.getValueAt(15,2).toString());
	        config.PIN3_RED = Double.parseDouble(model.getValueAt(15,3).toString());
	        config.PIN4_RED = Double.parseDouble(model.getValueAt(15,4).toString());
	        config.PIN5_RED = Double.parseDouble(model.getValueAt(15,5).toString());
	        config.PIN6_RED = Double.parseDouble(model.getValueAt(15,6).toString());
	        

	        scriviConfig(config);

	        javax.swing.JOptionPane.showMessageDialog(this, "Configurazione salvata!");

	    } catch (Exception ex) {
	        javax.swing.JOptionPane.showMessageDialog(this, "Errore nei dati!");
	    }
	}
	private void creaPulsanteSalva() {

	    javax.swing.JButton btnSalva = new javax.swing.JButton("SALVA");

	    btnSalva.addActionListener(e -> salvaSuJson());

	    add(btnSalva, BorderLayout.SOUTH);
	}
	
	private void creaPannelloIP() {

	    javax.swing.JPanel panelTop = new javax.swing.JPanel();
	    panelTop.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

	    javax.swing.JLabel lblIP = new javax.swing.JLabel("ESP32 IP Address:");

	    txtIPAddress = new JTextField(15);
	    txtIPAddress.setText(config.IPAddress);  // <-- carica da JSON

	    panelTop.add(lblIP);
	    panelTop.add(txtIPAddress);

	    add(panelTop, BorderLayout.NORTH);
	}

    public static void main(String[] args) {
        // Avvio GUI in un thread sicuro
        SwingUtilities.invokeLater(() -> {
            new Configuration().setVisible(true);
            
        });
    }
}


