/*
 * Clasa principala a aplicatiei
 */
package agenda;

import database.MySQL;
import gui.GridBuilder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import carte.ActionListenerFactory;

public class CarteDeTelefon extends JFrame {

	private static final long serialVersionUID = 1L;

	private Connection conn;

	private DefaultTableModel model;
	private JTable tabelPopulat = new JTable();
	private int randSelectat = 0;

	private JTextField numeText = new JTextField(10);
	private JTextField prenumeText = new JTextField(10);
	private JTextField cnpText = new JTextField(10);
	private JTextField telefonText = new JTextField(10);
	private JTextField cautareText = new JTextField(10);
	
	private final ActionListenerFactory actionListenerFactory;

	protected CarteDeTelefon(Connection conn) {
		this.setConn(conn);
		actionListenerFactory = new ActionListenerFactory(this);
	}

	// Constructorul clasei in care se deschide conexiunea catre
	// baza de date si se apeleaza metoda de afisare
	public CarteDeTelefon() {
		this(MySQL.getConnection());
		afisare();
	}

	/*
	 * Metoda principala de afisare a tuturor componentelor Tipul de layout
	 * folosit: GridBagLayout Contine panourile pentru tabel, butoane si
	 * casutele de adaugare abonat
	 */
	public void afisare() {
		getActionListenerFactory().getAnuleazaInput().actualizareTabel();
		getActionListenerFactory().getAnuleazaInput().dezactivareInput();
		JPanel panouPrincipal = new JPanel(new GridBagLayout());
		this.getContentPane().add(panouPrincipal);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 0, 0);

		JLabel eticheta = new JLabel("Abonati");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panouPrincipal.add(eticheta, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panouPrincipal.add(new JScrollPane(getTabelPopulat()), gbc);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		panouPrincipal.add(interfataButoaneTabel(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		panouPrincipal.add(interfataButoanePrincipale(), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		panouPrincipal.add(interfataAdaugare(), gbc);

		this.pack();
		this.setVisible(true);
	}


	public int getRandSelectat() {
		int rand = getTabelPopulat().getSelectedRow();

		/*
		 * La afisarea tabelului se foloseste randul selectat. Dupa modificarea
		 * datelor, valoarea variabilei "rand" devenea -1 si astfel am folosit
		 * variabila statica randSelectat pentru pastarea valorii randului
		 * selectat
		 */
		if (rand == -1) {
			rand = randSelectat;
		}
		return rand;
	}

	// interfata din partea dreapta a aplicatie pentru adaugare/modificare
	// abonati
	private JPanel interfataAdaugare() {
		JPanel panouInterfataAdaugare = new JPanel();
		panouInterfataAdaugare.setMinimumSize(new Dimension(200, 200));
		JLabel cautareLabel = new JLabel("Cauta ");
		JButton cautaAbonat = new JButton("Cautare");
		cautaAbonat.addActionListener(getActionListenerFactory().getCautaAbonat());

		JLabel numeLabel = new JLabel("Nume");
		JLabel prenumeLabel = new JLabel("Prenume");
		JLabel cnpLabel = new JLabel("CNP");
		JLabel telefonLabel = new JLabel("Telefon");

		panouInterfataAdaugare.setLayout(new GridBagLayout());

		getCautareText().setMinimumSize(getCautareText().getPreferredSize());
		getNumeText().setMinimumSize(getCautareText().getPreferredSize());
		getPrenumeText().setMinimumSize(getCautareText().getPreferredSize());
		getCnpText().setMinimumSize(getCautareText().getPreferredSize());
		getTelefonText().setMinimumSize(getCautareText().getPreferredSize());

		new GridBuilder().withColumns(2).withContainer(panouInterfataAdaugare)
			.withObject(cautareLabel)
			.withHorizontal(getCautareText())
			.withNone(numeLabel)
			.withHorizontal(getNumeText())
			.withNone(prenumeLabel)
			.withHorizontal(getPrenumeText(), 2)
			.withNone(cnpLabel, 1)
			.withHorizontal(getCnpText(), 2)
			.withNone(telefonLabel, 1)
			.withHorizontal(getTelefonText(), 2)
			.withHorizontal(cautaAbonat);

		return panouInterfataAdaugare;
	}

	// interfata de afisare a butoanelor principale
	private JPanel interfataButoanePrincipale() {
		JPanel panouButoanePrincipale = new JPanel(new GridBagLayout());
		JButton adaugaAbonat = new JButton("Activare");
		JButton salveazaAbonat = new JButton("Adaugare");
		JButton stergeAbonat = new JButton("Stergere");
		JButton actualizeazaAbonat = new JButton("Actualizare");
		JButton anuleazaInregistrare = new JButton("Anulare");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(1, 1, 1, 1);

		adaugaAbonat.addActionListener(getActionListenerFactory().getActivateInput());
		salveazaAbonat.addActionListener(getActionListenerFactory().getSalveazaAbonat());
		stergeAbonat.addActionListener(getActionListenerFactory().getStergeAbonat());
		actualizeazaAbonat.addActionListener(getActionListenerFactory().getActualizeazaAbonat());
		anuleazaInregistrare.addActionListener(getActionListenerFactory().getAnuleazaInput());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouButoanePrincipale.add(adaugaAbonat, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		panouButoanePrincipale.add(salveazaAbonat, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		panouButoanePrincipale.add(stergeAbonat, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		panouButoanePrincipale.add(actualizeazaAbonat, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouButoanePrincipale.add(anuleazaInregistrare, gbc);

		return panouButoanePrincipale;
	}

	// interfata de afisare a butoanelor din tabel - fara functionalitate inca
	private JPanel interfataButoaneTabel() {
		JPanel panouButoaneTabel = new JPanel();

		panouButoaneTabel.add(new JButton("Adaugare"));
		panouButoaneTabel.add(new JButton("Stergere"));
		panouButoaneTabel.add(new JButton("Modificare"));

		return panouButoaneTabel;
	}

	public static void main(String[] args) {
		CarteDeTelefon agenda = new CarteDeTelefon();
		agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		agenda.setLocationRelativeTo(null);
		agenda.setTitle("Agenda Telefonica");
		agenda.pack();
		agenda.setVisible(true);
	}

	/**
	 * @return the tabelPopulat
	 */
	public JTable getTabelPopulat() {
		return tabelPopulat;
	}

	/**
	 * @param tabelPopulat the tabelPopulat to set
	 */
	public void setTabelPopulat(JTable tabelPopulat) {
		this.tabelPopulat = tabelPopulat;
	}

	/**
	 * @return the model
	 */
	public DefaultTableModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	/**
	 * @return the numeText
	 */
	public JTextField getNumeText() {
		return numeText;
	}

	/**
	 * @param numeText the numeText to set
	 */
	public void setNumeText(JTextField numeText) {
		this.numeText = numeText;
	}

	/**
	 * @return the prenumeText
	 */
	public JTextField getPrenumeText() {
		return prenumeText;
	}

	/**
	 * @param prenumeText the prenumeText to set
	 */
	public void setPrenumeText(JTextField prenumeText) {
		this.prenumeText = prenumeText;
	}

	/**
	 * @return the cnpText
	 */
	public JTextField getCnpText() {
		return cnpText;
	}

	/**
	 * @param cnpText the cnpText to set
	 */
	public void setCnpText(JTextField cnpText) {
		this.cnpText = cnpText;
	}

	/**
	 * @return the telefonText
	 */
	public JTextField getTelefonText() {
		return telefonText;
	}

	/**
	 * @param telefonText the telefonText to set
	 */
	public void setTelefonText(JTextField telefonText) {
		this.telefonText = telefonText;
	}

	/**
	 * @return the cautareText
	 */
	public JTextField getCautareText() {
		return cautareText;
	}

	/**
	 * @param cautareText the cautareText to set
	 */
	public void setCautareText(JTextField cautareText) {
		this.cautareText = cautareText;
	}

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * @param randSelectat the randSelectat to set
	 */
	public void setRandSelectat(int randSelectat) {
		this.randSelectat = randSelectat;
	}

	/**
	 * @return the actionListenerFactory
	 */
	public ActionListenerFactory getActionListenerFactory() {
		return actionListenerFactory;
	}

}
