package agenda;

import database.MySQL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class CarteDeTelefon {
	
	private ArrayList<Abonat> abonat = new ArrayList<Abonat>();
	private TipNumarTelefon nrTel = new TipNumarTelefon();
	private Connection conn;
	private Statement sent;
	private JFrame frame; 
	private JPanel panouIntroducere; 
	private JScrollPane panouAfisare;
	private JTable tabelDate;
	private JPanel panouButoane;
	private JButton adaugaAbonat;
	private JButton salveazaAbonat;
	private JButton stergeAbonat;
	private JButton actualizeazaAbonat;
	private JButton anuleazaInregistrare;
	
	
	JLabel numeLabel = new JLabel("Nume");
	JLabel prenumeLabel = new JLabel("Prenume");
	JLabel cnpLabel = new JLabel("CNP");
	JLabel telefonLabel = new JLabel("Telefon");
	
	JTextField numeText = new JTextField();
	JTextField prenumeText = new JTextField();
	JTextField cnpText = new JTextField();
	JTextField telefonText = new JTextField();
	
	String[] capTabel = {"Nume","Prenume","CNP","Telefon"};
	Object[][] data = {
		    {"Kathy", "Smith",
		     "Snowboarding", new Integer(5), new Boolean(false)},
		    {"John", "Doe",
		     "Rowing", new Integer(3), new Boolean(true)},
		    {"Sue", "Black",
		     "Knitting", new Integer(2), new Boolean(false)},
		    {"Jane", "White",
		     "Speed reading", new Integer(20), new Boolean(true)},
		    {"John", "Doe",
		     "Rowing", new Integer(3), new Boolean(true)},
		    {"Sue", "Black",
		     "Knitting", new Integer(2), new Boolean(false)},
		    {"Jane", "White",
		     "Speed reading", new Integer(20), new Boolean(true)},
		    {"John", "Doe",
		     "Rowing", new Integer(3), new Boolean(true)},
		    {"Sue", "Black",
		     "Knitting", new Integer(2), new Boolean(false)},
		    {"Jane", "White",
		     "Speed reading", new Integer(20), new Boolean(true)},
		    {"Joe", "Brown",
		     "Pool", new Integer(10), new Boolean(false)}
		};

	private void btnAdaugaAbonatActionPerformed(ActionEvent ev) {
		try {
			String adaugaQuery = "INSERT INTO ABONAT(nume,prenume,cnp,telefon)"
					+ "VALUES(?,?,?,?)";
			
			PreparedStatement ps = conn.prepareStatement(adaugaQuery);
			ps.setString(1, numeText.getText());
			ps.setString(2, prenumeText.getText());
			ps.setString(3, cnpText.getText());
			ps.setString(4, telefonText.getText());
			
			int n = ps.executeUpdate();
			if(n>0) {
			    	JOptionPane.showMessageDialog(null, "Date salvate cu succes!");
			}
		} catch(SQLException ex) {
		    	JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
			
		}
	}
	
	private void activareInput() {
		 numeText.setEnabled(true);
		 prenumeText.setEnabled(true);
		 cnpText.setEnabled(true);
		 telefonText.setEnabled(true);
	}
	
	private void dezactivareInput() {
		 numeText.setEnabled(false);
		 numeText.setText(null);
		 
		 prenumeText.setEnabled(false);
		 prenumeText.setText(null);
		 
		 cnpText.setEnabled(false);
		 cnpText.setText(null);
		 
		 telefonText.setEnabled(false);
		 telefonText.setText(null);
	}
	
	public void afisare() {
		 frame = new JFrame();
		 panouIntroducere = new JPanel();
		 panouButoane = new JPanel();
		 tabelDate = new JTable(data,capTabel);
		 panouAfisare = new JScrollPane(tabelDate);
		 
		 dezactivareInput();
		 
		 GridLayout layoutPrincipal = new GridLayout(2, 1);
                 GridLayout layoutIntroducere = new GridLayout(4, 2);
                 GridLayout layoutButoane = new GridLayout(1, 5);
                 
                 adaugaAbonat = new JButton("Adaugare");
                 adaugaAbonat.addActionListener(
                	 new ActionListener() {
			    
			    @Override
			    public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				activareInput();
			    }
			}
                	 );
                 
                 salveazaAbonat = new JButton("Salvare");
                 salveazaAbonat.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				String adaugaQuery = "INSERT INTO ABONAT(nume,prenume,cnp,telefon)"
						+ "VALUES(?,?,?,?)";
				
				PreparedStatement ps = conn.prepareStatement(adaugaQuery);
				ps.setString(1, numeText.getText());
				ps.setString(2, prenumeText.getText());
				ps.setString(3, cnpText.getText());
				ps.setString(4, telefonText.getText());
				
				int n = ps.executeUpdate();
				if(n>0) {
				    	JOptionPane.showMessageDialog(null, "Date salvate cu succes!");
				}
			} catch(SQLException ex) {
			    	JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
				
			}
		    }
		});
                 
                 stergeAbonat = new JButton("Stergere");
                 stergeAbonat.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		    }
		});
                 
                 actualizeazaAbonat = new JButton("Actualizare");
                 actualizeazaAbonat.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		    }
		});
                 
                 anuleazaInregistrare = new JButton("Anulare");
                 anuleazaInregistrare.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			dezactivareInput();
		    }
		});
                 panouIntroducere.setLayout(layoutIntroducere);
                 panouButoane.setLayout(layoutButoane);
                 
                 panouIntroducere.setPreferredSize(new Dimension(600, 200));
                 panouIntroducere.setMinimumSize(new Dimension(600, 200));
                 panouIntroducere.setMaximumSize(new Dimension(600, 200));
                 
                 panouAfisare.setPreferredSize(new Dimension(600,200));
                 panouAfisare.setMinimumSize(new Dimension(600, 200));
                 panouAfisare.setMaximumSize(new Dimension(600, 200));
                 
                 panouButoane.setPreferredSize(new Dimension(600,100));
                 panouButoane.setMinimumSize(new Dimension(600, 100));
                 panouButoane.setMaximumSize(new Dimension(600, 100));                 
                 
                 panouIntroducere.add(numeLabel);
                 panouIntroducere.add(numeText);
                 
                 panouIntroducere.add(prenumeLabel);
                 panouIntroducere.add(prenumeText);
                 
                 panouIntroducere.add(cnpLabel);
                 panouIntroducere.add(cnpText);
                 
                 panouIntroducere.add(telefonLabel);
                 panouIntroducere.add(telefonText);
                 
		 panouIntroducere.setVisible(true);
		 
		 panouButoane.add(adaugaAbonat);
		 panouButoane.add(salveazaAbonat);
		 panouButoane.add(stergeAbonat);
		 panouButoane.add(actualizeazaAbonat);
		 panouButoane.add(anuleazaInregistrare);
		 
		 panouButoane.setVisible(true);
		 
		 tabelDate.setFillsViewportHeight(true);
		 
		 frame.setLayout(layoutPrincipal);
		 frame.setSize(600, 500);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.setTitle("Agenda Telefonica");
		 frame.add(panouIntroducere,BorderLayout.NORTH);
		 frame.add(panouAfisare,BorderLayout.CENTER);
		 frame.add(panouButoane, BorderLayout.SOUTH);
		 frame.setVisible(true);		 
	}
	
	public CarteDeTelefon() {
		conn = MySQL.getConnection();
		afisare();
		
		
	}
	
	private void adaugareAbonat() {

		String nume = numeText.getText();;
		String prenume = prenumeText.getText();
		String cnp = cnpText.getText();
		String telefon = telefonText.getText();	
		
		NrTel numar = nrTel.getTipNumarTel(telefon);
		Abonat abonatUnic = new Abonat(nume, prenume, cnp, numar);  
		abonat.add(abonatUnic);
		
		Iterator<Abonat> inregistareAbonat = abonat.iterator();
		
		while(inregistareAbonat.hasNext()) {
			Abonat abonatIterat = inregistareAbonat.next();
			try {
				String adaugaQuery = "INSERT INTO ABONAT(nume,prenume,cnp,telefon)"
						+ "VALUES(?,?,?,?)";
				
				PreparedStatement ps = conn.prepareStatement(adaugaQuery);
				ps.setString(1, abonatIterat.getNume());
				ps.setString(2, abonatIterat.getPrenume());
				ps.setString(3, abonatIterat.getCnp());
				ps.setString(4, abonatIterat.getNumarTelefon().toString());
				
				int n = ps.executeUpdate();
				if(n>0) {
					System.out.println("Datele au fost adaugate cu succes!");
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
/*		try {
			File file = new File("D:\\NRS\\AgendaTelefonica\\agenda.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(abonat.toString());
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}*/

	}
	
	private void cautareAbonat() {
		//TODO
	}
	
	private void stergeAbonat() {
		//TODO
	}
	
	private void modificaAbonat() {
		//TODO
	}
	
	private void afisareAbonati(){
		try {
			String afiseazaQuery = "SELECT * FROM ABONAT";
			sent = conn.createStatement();
			ResultSet rs = sent.executeQuery(afiseazaQuery);
			String[] abonat = new String[5];
			System.out.println("ID Nume Prenume CNP Telefon");
			while(rs.next()) {
				abonat[0] = rs.getString("id");
				abonat[1] = rs.getString("nume");
				abonat[2] = rs.getString("prenume");
				abonat[3] = rs.getString("cnp");
				abonat[4] = rs.getString("telefon");
				System.out.println(abonat[0] + " " + abonat[1] + " " + abonat[2] + " " + abonat[3] + " " + abonat[4]);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		Iterator<Abonat> inregistareAbonat = abonat.iterator();
		while(inregistareAbonat.hasNext()) {
			System.out.println(inregistareAbonat.next().toString());
		}
	}
	
	public static void main(String[] args) {
			new CarteDeTelefon();
	}
	
}
