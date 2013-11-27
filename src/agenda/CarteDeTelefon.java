package agenda;

import database.MySQL;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class CarteDeTelefon extends JFrame{
    

    private static final long serialVersionUID = 1L;
    
    private TipNumarTelefon nrTel = new TipNumarTelefon();
    private Connection conn;
    private Statement stmt;
    
    private DefaultTableModel model;
    private JTable tabelPopulat = new JTable();
    private static int randSelectat = 0;
    
    private JTextField numeText = new JTextField(10);
    private JTextField prenumeText = new JTextField(10);
    private JTextField cnpText = new JTextField(10);
    private JTextField telefonText = new JTextField(10);
    private JTextField cautareText = new JTextField(10);
    
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
	    	stergeAbonat();
	    }
	}

	class ActualizeazaAbonat implements ActionListener {
	    public void actionPerformed(ActionEvent arg0) {
	        actualizeazaAbonat();
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
	
	class SelectieTabel implements ListSelectionListener {
			@Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                	selecteazaRand();
                }
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
    	cautareText.setText(null);
        numeText.setText(null);
        prenumeText.setText(null);
        cnpText.setText(null);
        telefonText.setText(null);
    }
    
    void actualizareTabel() {
		try {
		    String[] coloane = {"Nr.#","Nume","Prenume","CNP","Telefon"};
		    String sql = "select * from abonat";
		    model = new DefaultTableModel(null,coloane);
		    stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		    
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
		    tabelPopulat.getSelectionModel().addListSelectionListener(new SelectieTabel());
		} catch(Exception ex) {
		    ex.printStackTrace();
		}
    }
    
    void selecteazaRand() {
		activareInput();
    	try {
    		int rand = tabelPopulat.getSelectedRow();
    		
    		//La afisarea tabelului se foloseste randul selectat. Dupa modificarea
    		//datelor, valoarea variabilei "rand" devenea
    		if(rand==-1) {
    			rand=randSelectat;
    		}
    		String sql = "select * from abonat where id=" +tabelPopulat.getValueAt(rand, 0);
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery(sql);
    		rs.next();
    		numeText.setText(rs.getString("nume"));
    		prenumeText.setText(rs.getString("prenume"));
    		cnpText.setText(rs.getString("cnp"));
    		telefonText.setText(rs.getString("telefon"));
    		
    		randSelectat=rand;
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Eroare: "+ex.getMessage());
    	}

    }
    
    private JPanel interfataAdaugare() {
	JPanel panouInterfataAdaugare = new JPanel();
	panouInterfataAdaugare.setMinimumSize(new Dimension(200, 200));
	JLabel cautareLabel = new JLabel("Cauta ");
    JButton cautaAbonat = new JButton("Cautare");
    cautaAbonat.addActionListener(new CautaAbonat());

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
	
	i++;
	
	gbc.gridx = 1;
    gbc.gridy = i;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panouInterfataAdaugare.add(cautaAbonat,gbc);

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


        adaugaAbonat.addActionListener(new ActivareInput());
        salveazaAbonat.addActionListener(new SalveazaAbonat());
        stergeAbonat.addActionListener(new StergeAbonat());
        actualizeazaAbonat.addActionListener(new ActualizeazaAbonat());
        anuleazaInregistrare.addActionListener(new AnuleazaInput());
        
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
    
    private void adaugareAbonat(){
        
    	NrTel numar = null;
        String nume = numeText.getText();
        String prenume = prenumeText.getText();
        String cnp = cnpText.getText();
        String telefon = telefonText.getText();    


        
        
        if((nume!=null && nume.length() > 0) && (prenume!=null && prenume.length() > 0) 
            && (cnp!=null && cnp.length() > 0) && (telefon!=null && telefon.length() > 0)) {
            
        	try {
                numar = nrTel.getTipNumarTel(telefon);
            } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Eroare: " + e.getMessage());
            }
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
        } else {
            JOptionPane.showMessageDialog(null, "Completati toate campurile!");
        }
    }
        
    private void cautareAbonat() {
    	
    	try {
    		String textCautat = cautareText.getText().trim();
    		if (textCautat!=null & textCautat.length()>0) {
	    		String sql = "SELECT nume,prenume,cnp,telefon from abonat "
	    				+ "where nume like '%"+textCautat+"%' "
	    				+ "or prenume like '%"+textCautat+"%' "
	    				+ "or cnp like '%"+textCautat+"%' "
	    				+ "or telefon like '%"+textCautat+"%'";
	    		String sqlRezultate = "SELECT COUNT(*) as rezultate FROM ( "+sql+" ) as inregistrari";
	    		stmt = conn.createStatement();

	    		int rezultate = 0;
	    		ResultSet rsRezultate = stmt.executeQuery(sqlRezultate);
	    		rsRezultate.next();
	    		rezultate = rsRezultate.getInt(1);
        		rsRezultate.close();
        		
	    		ResultSet rs = stmt.executeQuery(sql);
        		if(rezultate==1) {
        			rs.next();
		    		numeText.setText(rs.getString("nume"));
		    		prenumeText.setText(rs.getString("prenume"));
		    		cnpText.setText(rs.getString("cnp"));
		    		telefonText.setText(rs.getString("telefon"));
	    		} else if(rezultate>1) {
	    			JOptionPane.showMessageDialog(null, "Am gasit : " + rezultate +" rezultate. Cautati dupa telefon sau CNP pentru rezultate unice!");
        			rs.next();
		    		numeText.setText(rs.getString("nume"));
		    		prenumeText.setText(rs.getString("prenume"));
		    		cnpText.setText(rs.getString("cnp"));
		    		telefonText.setText(rs.getString("telefon"));
	    		} else {
	        		JOptionPane.showMessageDialog(null, "Niciun rezultat gasit!");
	    		}
    		} else {
        		JOptionPane.showMessageDialog(null, "Completati campul de cautare!");
    		}
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Eroare: "+ex.getMessage());
    	}
    	
    }
   
    private void stergeAbonat() {
	        try {
	            int rand = tabelPopulat.getSelectedRow();
		            String stergeSQL = "DELETE FROM ABONAT "
		                    + " WHERE id=" + tabelPopulat.getValueAt(rand, 0);
		            stmt = conn.createStatement();
		            int n = stmt.executeUpdate(stergeSQL);
		            if(n>0) {
		                stergereInput();
		                actualizareTabel();
		                dezactivareInput();
		                JOptionPane.showMessageDialog(null, "Abonat sters!");
		            }
	        } catch(SQLException ex) {
	                JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
	        }
    }
    
    private void actualizeazaAbonat() {
    
        String nume = numeText.getText();
        String prenume = prenumeText.getText();
        String cnp = cnpText.getText();
        String telefon = telefonText.getText(); 
        
        if((nume!=null && nume.length() > 0) && (prenume!=null && prenume.length() > 0) 
                && (cnp!=null && cnp.length() > 0) && (telefon!=null && telefon.length() > 0)) {
    	
	        try {
	            String adaugaQuery = "UPDATE ABONAT SET nume=?, prenume=?, cnp=? ,telefon=?"
	                    + " WHERE id=?";
	            int rand = tabelPopulat.getSelectedRow();
	            String id = (String) tabelPopulat.getValueAt(rand, 0);
	            PreparedStatement ps = conn.prepareStatement(adaugaQuery);
	            ps.setString(1, numeText.getText());
	            ps.setString(2, prenumeText.getText());
	            ps.setString(3, cnpText.getText());
	            ps.setString(4, telefonText.getText());
	            ps.setString(5, id);
	            int n = ps.executeUpdate();
	            if(n>0) {
	                stergereInput();
	            	actualizareTabel();
	                JOptionPane.showMessageDialog(null, "Date actualizate cu succes!");
	            }
	        } catch(SQLException ex) {
	                JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
	            
	        }
        } else {
            JOptionPane.showMessageDialog(null, "Cautati sau selectati un abonat!");
        }
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
