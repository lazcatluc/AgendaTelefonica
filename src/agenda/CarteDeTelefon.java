package agenda;

import database.MySQL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CarteDeTelefon extends JFrame{
    

    private static final long serialVersionUID = 1L;
    
    private TipNumarTelefon nrTel = new TipNumarTelefon();
    private static Connection conn;
    private static Statement sent;
    private JScrollPane panouAfisare;
    private JTable tabelDate;

    private JTextField numeText = new JTextField(10);
    private JTextField prenumeText = new JTextField(10);
    private JTextField cnpText = new JTextField(10);
    private JTextField telefonText = new JTextField(10);

	private DefaultTableModel model;
    
    public CarteDeTelefon() {
        conn = MySQL.getConnection();
        afisare();
        
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
    

    
    private JPanel interfataAdaugare() {
        JPanel panouInterfataAdaugare = new JPanel();
        panouInterfataAdaugare.setMinimumSize(new Dimension(200, 200));
		JLabel cautareLabel = new JLabel("Cauta ");
		JTextField cautareText = new JTextField(10);
		
		JLabel numeLabel = new JLabel("Nume");
	    JLabel prenumeLabel = new JLabel("Prenume");
	    JLabel cnpLabel = new JLabel("CNP");
	    JLabel telefonLabel = new JLabel("Telefon");
		
		panouInterfataAdaugare.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2,2,2,2);
		gbc.anchor = GridBagConstraints.NORTHEAST;
		
		cautareText.setMinimumSize(cautareText.getPreferredSize());
		numeText.setMinimumSize(cautareText.getPreferredSize());
		prenumeText.setMinimumSize(cautareText.getPreferredSize());
		cnpText.setMinimumSize(cautareText.getPreferredSize());
		telefonText.setMinimumSize(cautareText.getPreferredSize());
		
		
		int i=0;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		panouInterfataAdaugare.add(cautareLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(cautareText,gbc);
		
		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(numeLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(numeText,gbc);		

		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(prenumeLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(prenumeText,gbc);		

		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(cnpLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(cnpText,gbc);		

		i++;
		
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(telefonLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(telefonText,gbc);		

		
	return panouInterfataAdaugare;
    }
    
    
    private JPanel interfataButoanePrincipale() {
        JPanel panouButoanePrincipale = new JPanel(new GridBagLayout());
        JButton adaugaAbonat = new JButton("Adaugare");
        JButton salveazaAbonat = new JButton("Salvare");
        JButton stergeAbonat = new JButton("Stergere");
        JButton actualizeazaAbonat = new JButton("Actualizare");
        JButton anuleazaInregistrare = new JButton("Anulare");
        JButton cautaAbonat = new JButton("Cautare");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(1, 1, 1, 1);

        
        adaugaAbonat.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
            	activareInput();
            }
        });
        
        salveazaAbonat.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	adaugareAbonat();
	        }
	    });
        
        stergeAbonat.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            stergeAbonat();
	        }
	    });
        
        actualizeazaAbonat.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            modificaAbonat();
	        }
	    });
        
        anuleazaInregistrare.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            dezactivareInput();
	        }
        });
        
        cautaAbonat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cautareAbonat();
				
			}
		});
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panouButoanePrincipale.add(adaugaAbonat,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panouButoanePrincipale.add(salveazaAbonat,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        panouButoanePrincipale.add(stergeAbonat,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panouButoanePrincipale.add(actualizeazaAbonat,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panouButoanePrincipale.add(anuleazaInregistrare,gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridheight = 2;
        panouButoanePrincipale.add(cautaAbonat,gbc);
        
        return panouButoanePrincipale;

        
    }
    
    private JPanel interfataButoaneTabel() {
    	JPanel panouButoaneTabel = new JPanel();
    	
    	panouButoaneTabel.add(new JButton("Adaugare"));
    	panouButoaneTabel.add(new JButton("Stergere"));
    	panouButoaneTabel.add(new JButton("Modificare"));

    	return panouButoaneTabel;
    }
    
    public JScrollPane tabelAbonati() {
        
        tabelDate = new JTable(afisareAbonati());
        tabelDate.setFillsViewportHeight(true);
        panouAfisare = new JScrollPane(tabelDate);
        panouAfisare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        panouAfisare.setPreferredSize(new Dimension(400,300));
        
        return panouAfisare;
    }
    
    public void afisare() {
        
        dezactivareInput(); 
        JPanel panouPrincipal = new JPanel(new GridBagLayout());
        this.getContentPane().add(panouPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 0, 0);
        
        JLabel eticheta = new JLabel("Abonati");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panouPrincipal.add(eticheta,gbc);
       
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panouPrincipal.add(actualizareTabel(),gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panouPrincipal.add(interfataButoaneTabel(), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panouPrincipal.add(interfataButoanePrincipale(),gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTH;
        panouPrincipal.add(interfataAdaugare(), gbc);
        
        this.pack();
        this.setVisible(true);         
    }
    
    private void adaugareAbonat(){
        
    	NrTel numar = null;
        String nume = numeText.getText();;
        String prenume = prenumeText.getText();
        String cnp = cnpText.getText();
        String telefon = telefonText.getText();    

        try {
            numar = nrTel.getTipNumarTel(telefon);
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Eroare: " + e.getMessage());
        }
        
        
        if((nume!=null && nume.length() > 0) && (prenume!=null && prenume.length() > 0) 
            && (cnp!=null && cnp.length() > 0) && (telefon!=null && telefon.length() > 0)) {
        Abonat abonatUnic = new Abonat(nume, prenume, cnp, numar);  

            try {
                String adaugaQuery = "INSERT INTO ABONAT(nume,prenume,cnp,telefon)"
                        + "VALUES(?,?,?,?)";
                
                PreparedStatement ps = conn.prepareStatement(adaugaQuery);
                ps.setString(1, abonatUnic.getNume());
                ps.setString(2, abonatUnic.getPrenume());
                ps.setString(3, abonatUnic.getCnp());
                ps.setString(4, abonatUnic.getNumarTelefon().toString());
                
                int n = ps.executeUpdate();
                if(n>0) {
                		actualizareTabel();
                        JOptionPane.showMessageDialog(null, "Date salvate cu succes!");
                }
            } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
                
            }
        } 
        
    }
        
    private void cautareAbonat() {
    }
    
    private void stergeAbonat() {
    }
    
    private void modificaAbonat() {
    }
    
    public JScrollPane actualizareTabel() {
    	try {
    		
    	String[] coloane = {"ID","Nume","Prenume","CNP","Telefon"};
        String afiseazaQuery = "SELECT * FROM ABONAT";
        
        sent = conn.createStatement();
        ResultSet rs = sent.executeQuery(afiseazaQuery);
        
        String[] abonat = new String[5];
        while(rs.next()) {
        	abonat[0] = rs.getString("id");
        	abonat[1] = rs.getString("nume");
        	abonat[2] = rs.getString("prenume");
        	abonat[3] = rs.getString("cnp");
        	abonat[4] = rs.getString("telefon");
        	model.addRow(abonat);
        }
        model = new DefaultTableModel(null,coloane);
        tabelDate.setModel(model);
    	} catch(Exception e) {
    		
    	}
    	panouAfisare = new JScrollPane(tabelDate);
        panouAfisare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panouAfisare.setPreferredSize(new Dimension(400,300));
        
        return panouAfisare;
    }
    
    private static DefaultTableModel afisareAbonati(){
    	
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<String> numeColoane = new Vector<String>();
        DefaultTableModel tabelAbonati = new DefaultTableModel(data,numeColoane);
        numeColoane.add("Nr. #");
        numeColoane.add("Nume");
        numeColoane.add("Prenume");
        numeColoane.add("CNP");
        numeColoane.add("Telefon");
        
        
        try {
            String afiseazaQuery = "SELECT * FROM ABONAT";
            sent = conn.createStatement();
            ResultSet rs = sent.executeQuery(afiseazaQuery);
            while(rs.next()) {
            	Vector<Object> rand = new Vector<Object>(5);
            	for(int indexColoana = 1; indexColoana <=5; indexColoana ++) {
            		rand.add(rs.getObject(indexColoana));
            	}
                data.add(rand);
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return tabelAbonati;
    }
    
    public static void main(String[] args) {
            CarteDeTelefon agenda = new CarteDeTelefon();
            
            agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            agenda.setLocationRelativeTo ( null );
            agenda.setTitle("Agenda Telefonica");
            agenda.pack();
            agenda.setVisible(true);
    }
    
}
