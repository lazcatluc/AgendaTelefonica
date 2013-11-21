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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CarteDeTelefon extends JFrame{
    

    private static final long serialVersionUID = 1L;
    
    private ArrayList<Abonat> abonat = new ArrayList<Abonat>();
    private TipNumarTelefon nrTel = new TipNumarTelefon();
    private Connection conn;
    private Statement sent;
    private JPanel panouIntroducere; 
    private JScrollPane panouAfisare;
    private JTable tabelDate;
    private JPanel panouButoane;
    private JButton adaugaAbonat;
    private JButton salveazaAbonat;
    private JButton stergeAbonat;
    private JButton actualizeazaAbonat;
    private JButton anuleazaInregistrare;

    private JLabel numeLabel = new JLabel("Nume");
    private JLabel prenumeLabel = new JLabel("Prenume");
    private JLabel cnpLabel = new JLabel("CNP");
    private JLabel telefonLabel = new JLabel("Telefon");

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
    

    
    private JPanel panouIntroducere() {
	panouIntroducere = new JPanel(new GridBagLayout());
	panouIntroducere.setPreferredSize(new Dimension(400,300));

        GridBagConstraints gbc = new GridBagConstraints();

        int i=0;
        
        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        


        gbc.gridx = 0;
        gbc.gridy = i;
        panouIntroducere.add(numeLabel,gbc);

        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panouIntroducere.add(numeText,gbc);       
 
        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panouIntroducere.add(prenumeLabel,gbc);


        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panouIntroducere.add(prenumeText,gbc);      
 
        i++;

        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panouIntroducere.add(cnpLabel,gbc);


        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panouIntroducere.add(cnpText,gbc);       
 
        i++;


        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panouIntroducere.add(telefonLabel,gbc);    
        
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panouIntroducere.add(telefonText,gbc);
 
        return panouIntroducere;
    }
    
    private JPanel panouButoane() {
        panouButoane = new JPanel(new GridBagLayout());
        
        adaugaAbonat = new JButton("Adaugare");
        salveazaAbonat = new JButton("Salvare");
        stergeAbonat = new JButton("Stergere");
        actualizeazaAbonat = new JButton("Actualizare");
        anuleazaInregistrare = new JButton("Anulare");
        
        adaugaAbonat.addActionListener(
            new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            activareInput();
            }
        }
            );
        
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
            

        panouButoane.setPreferredSize(new Dimension(500,100));

        panouButoane.add(adaugaAbonat);
        panouButoane.add(salveazaAbonat);
        panouButoane.add(stergeAbonat);
        panouButoane.add(actualizeazaAbonat);
        panouButoane.add(anuleazaInregistrare);
        
        return panouButoane;

        
    }
    
    public JScrollPane tabelAbonati() {
        String[] capTabel = {"Nume","Prenume","CNP","Telefon"};
        Object[][] data = {{"Liviu","Jianu","",""},{"Ana","Puiut","",""}};
        tabelDate = new JTable(data,capTabel);
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
        JLabel label = new JLabel("Abonati");

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panouPrincipal.add(label,gbc);
       
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panouPrincipal.add(tabelAbonati(),gbc);
        
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panouPrincipal.add(panouButoane(), gbc);
        
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;

        panouPrincipal.add(panouIntroducere(), gbc);
        
        this.pack();
        this.setVisible(true);         
    }
    
    private void adaugareAbonat() {

        String nume = numeText.getText();;
        String prenume = prenumeText.getText();
        String cnp = cnpText.getText();
        String telefon = telefonText.getText();    
        
        NrTel numar = null;
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
        } else {
            JOptionPane.showMessageDialog(null, "Completati toate campurile!");
            }
        
        }
        
/*        try {
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
                Object[] inregistrariDB = {abonat[0],abonat[1],abonat[2],abonat[3],abonat[4]};
                
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
            CarteDeTelefon agenda = new CarteDeTelefon();
            //agenda.setSize(600, 500);
            agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            agenda.setTitle("Agenda Telefonica");
            agenda.pack();
            agenda.setVisible(true);
    }
    
}
