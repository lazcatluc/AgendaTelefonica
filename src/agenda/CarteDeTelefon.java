package agenda;

import database.MySQL;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class CarteDeTelefon extends JFrame{
    

    private static final long serialVersionUID = 1L;
    
    private TipNumarTelefon nrTel = new TipNumarTelefon();
    private static Connection conn;
    private static Statement sent;
    
    private DefaultTableModel model;
    private JTable tabelPopulat = new JTable();
    
    private JTextField numeText = new JTextField(10);
    private JTextField prenumeText = new JTextField(10);
    private JTextField cnpText = new JTextField(10);
    private JTextField telefonText = new JTextField(10);

    public CarteDeTelefon() {
        conn = MySQL.getConnection();
        afisare();
        
    }
    
    class ActivareInput implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
	    	activareInput();
	    }
	}

	class SalveazaAbonat implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
	    	adaugareAbonat();
	    	stergereInput();
	    	actualizareTabel();
}
	}

	class StergeAbonat implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
	    	stergereInput();
	    }
	}

	class ActualizeazaAbonat implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
	        modificaAbonat();
	    }
	}

	class AnuleazaInput implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
	    	stergereInput();
	        dezactivareInput();        
	    }
	}

	class CautaAbonat implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
			cautareAbonat();
	    }
	}


	public void afisare() {
	    actualizareTabel();
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
	    panouPrincipal.add(new JScrollPane(tabelPopulat),gbc);
	    
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

	private void activareInput() {
         numeText.setEnabled(true);
         prenumeText.setEnabled(true);
         cnpText.setEnabled(true);
         telefonText.setEnabled(true);
    }
    
    private void dezactivareInput() {
         numeText.setEnabled(false);
         prenumeText.setEnabled(false);
         cnpText.setEnabled(false);
         telefonText.setEnabled(false);
    }
    
    private void stergereInput() {
        numeText.setText(null);
        prenumeText.setText(null);
        cnpText.setText(null);
        telefonText.setText(null);
    }
    
    void actualizareTabel() {
		try {
		    conn = MySQL.getConnection();
		    String[] coloane = {"Nr.#","Nume","Prenume","CNP","Telefon"};
		    String sql = "select * from abonat";
		    model = new DefaultTableModel(null,coloane);
		    sent = conn.createStatement();
		    ResultSet rs = sent.executeQuery(sql);
		    
		    String[] inregistrare = new String[5];
		    
		    while(rs.next()) {
			inregistrare[0] = rs.getString("id");
			inregistrare[1] = rs.getString("nume");
			inregistrare[2] = rs.getString("prenume");
			inregistrare[3] = rs.getString("cnp");
			inregistrare[4] = rs.getString("telefon");
			model.addRow(inregistrare);
		    }
		    tabelPopulat.setModel(model);
		    tabelPopulat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		} catch(Exception ex) {
		    ex.printStackTrace();
		}
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


        adaugaAbonat.addActionListener(new ActivareInput());
        salveazaAbonat.addActionListener(new SalveazaAbonat());
        stergeAbonat.addActionListener(new StergeAbonat());
        actualizeazaAbonat.addActionListener(new ActualizeazaAbonat());
        anuleazaInregistrare.addActionListener(new AnuleazaInput());
        cautaAbonat.addActionListener(new CautaAbonat());
        
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
    
    private void adaugareAbonat(){
        
    	NrTel numar = null;
        String nume = numeText.getText();
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
                    stergereInput();
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
    

    
    public static void main(String[] args) {
            CarteDeTelefon agenda = new CarteDeTelefon();
            
            agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            agenda.setLocationRelativeTo ( null );
            agenda.setTitle("Agenda Telefonica");
            agenda.pack();
            agenda.setVisible(true);
    }
    
}
