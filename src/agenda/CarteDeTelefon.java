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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
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
    private Connection conn;
    private Statement sent;
    private JScrollPane panouAfisare;
    private JTable tabelDate;

    private JTextField numeText = new JTextField(10);
    private JTextField prenumeText = new JTextField(10);
    private JTextField cnpText = new JTextField(10);
    private JTextField telefonText = new JTextField(10);
    
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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(1, 1, 1, 1);

        
        adaugaAbonat.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            activareInput();
            }
        });
        
        salveazaAbonat.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        // TODO Auto-generated method stub
			adaugareAbonat();
	        }
	    });
        
        stergeAbonat.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        // TODO Auto-generated method stub
	            stergeAbonat();
	        }
	    });
        
        actualizeazaAbonat.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        // TODO Auto-generated method stub
	            modificaAbonat();
	        }
	    });
        
        anuleazaInregistrare.addActionListener(new ActionListener() {
        
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        // TODO Auto-generated method stub
	            dezactivareInput();
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
        Vector<String> numeColoane = new Vector<String>();
        numeColoane.add("Nr. #");
        numeColoane.add("Nume");
        numeColoane.add("Prenume");
        numeColoane.add("CNP");
        numeColoane.add("Telefon");
        numeColoane.add("\n");
        Vector<Vector<Object>> data = afisareAbonati();
        System.out.println(data);
        
        
        tabelDate = new JTable(data,numeColoane);
        panouAfisare = new JScrollPane(tabelDate);
        panouAfisare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tabelDate.setFillsViewportHeight(true);
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
        panouPrincipal.add(tabelAbonati(),gbc);
        
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
                        JOptionPane.showMessageDialog(null, "Date salvate cu succes!");
                }
            } catch(SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
                
            }
        } 
        
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
    
    private Vector<Vector<Object>> afisareAbonati(){
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Object> rand;
        try {
            String afiseazaQuery = "SELECT * FROM ABONAT";
            sent = conn.createStatement();
            ResultSet rs = sent.executeQuery(afiseazaQuery);
            rand = new Vector<>(5);
            while(rs.next()) {
            	rand.add(rs.getString("id"));
                rand.add(rs.getString("nume"));
                rand.add(rs.getString("prenume"));
                rand.add(rs.getString("cnp"));
                rand.add(rs.getString("telefon"));
                data.add(rand);
                
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return data;
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
