/*
 * Clasa principala a aplicatiei
 */
package agenda;

import database.MySQL;

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

public class CarteDeTelefon extends JFrame {

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

	protected CarteDeTelefon(Connection conn) {
		this.conn = conn;
	}

	// Constructorul clasei in care se deschide conexiunea catre
	// baza de date si se apeleaza metoda de afisare
	public CarteDeTelefon() {
		this(MySQL.getConnection());
		afisare();

	}

	// Clasele interioare pentru functionalitate
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

	/*
	 * Clasa pentru selectarea randului din tabel care implementeaza
	 * ListSelectionListener pentru completarea datelor in campurile de input
	 * dupa selectarea unui rand
	 */
	class SelectieTabel implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				selecteazaRand();
			}
		}
	}

	/*
	 * Clasa pentru stergerea abonatului la apasarea tastei DELETEDupa apasarea
	 * tastei, utilizatorul este intrebat dacadoreste stergerea
	 */
	class StergereRandAbonat implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			int c = e.getKeyCode();
			if (c == KeyEvent.VK_DELETE) {
				int valoareMesaj = JOptionPane.showConfirmDialog(tabelPopulat,
						"Doriti stergerea abonatului?", "Confirmati stergerea",
						JOptionPane.YES_NO_OPTION);
				int[] index = tabelPopulat.getSelectedRows();
				e.consume();
				stergeAbonat();
				if (valoareMesaj == JOptionPane.YES_OPTION) {
					for (int i = index.length - 1; i >= 0; --i) {
						model.removeRow(index[i]);
					}
				} else if (valoareMesaj == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null,
							"Abonatul nu a fost sters!");
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/*
	 * Metoda principala de afisare a tuturor componentelor Tipul de layout
	 * folosit: GridBagLayout Contine panourile pentru tabel, butoane si
	 * casutele de adaugare abonat
	 */
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
		panouPrincipal.add(eticheta, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		panouPrincipal.add(new JScrollPane(tabelPopulat), gbc);

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

	// metoda de activare a input-urilor de introducere a datelor
	private void activareInput() {
		numeText.setEnabled(true);
		prenumeText.setEnabled(true);
		cnpText.setEnabled(true);
		telefonText.setEnabled(true);
	}

	// metoda de dezactivare a input-urilor de introducere a datelor
	private void dezactivareInput() {
		numeText.setEnabled(false);
		prenumeText.setEnabled(false);
		cnpText.setEnabled(false);
		telefonText.setEnabled(false);
	}

	// metoda de stergere a input-urilor de introducere a datelor
	private void stergereInput() {
		cautareText.setText(null);
		numeText.setText(null);
		prenumeText.setText(null);
		cnpText.setText(null);
		telefonText.setText(null);
	}

	// metoda pentru actualizarea informatiilor din tabel la schimbarea
	// datelor (adaugare/modificare/stergere)
	void actualizareTabel() {
		try {
			String[] coloane = { "Nr.#", "Nume", "Prenume", "CNP", "Telefon" };
			String sql = "select * from abonat";
			model = new DefaultTableModel(null, coloane);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// folosirea TableRowSorter pentru a ordona datele din tabel prin
			// selectarea unui titlu de coloana
			final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
					model);
			tabelPopulat.setRowSorter(sorter);

			// adaugare listener pentru input-ul de cautare pentru
			// filtrarea datelor din tabel
			cautareText.getDocument().addDocumentListener(
					new DocumentListener() {
						private void searchFieldChangedUpdate(DocumentEvent evt) {
							String text = cautareText.getText();
							if (text.length() == 0) {
								sorter.setRowFilter(null);
							} else {
								try {
									sorter.setRowFilter(RowFilter
											.regexFilter("(?i)" + text));
								} catch (PatternSyntaxException pse) {
									JOptionPane.showMessageDialog(null,
											"Bad regex pattern",
											"Bad regex pattern",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						}

						@Override
						public void insertUpdate(DocumentEvent evt) {
							searchFieldChangedUpdate(evt);
						}

						@Override
						public void removeUpdate(DocumentEvent evt) {
							searchFieldChangedUpdate(evt);
						}

						@Override
						public void changedUpdate(DocumentEvent evt) {
							searchFieldChangedUpdate(evt);
						}
					});

			String[] linie = new String[5];

			while (rs.next()) {
				linie[0] = rs.getString("id");
				linie[1] = rs.getString("nume");
				linie[2] = rs.getString("prenume");
				linie[3] = rs.getString("cnp");
				linie[4] = rs.getString("telefon");
				model.addRow(linie);
			}
			tabelPopulat.setModel(model);
			tabelPopulat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelPopulat.getSelectionModel().addListSelectionListener(
					new SelectieTabel());
			tabelPopulat.addKeyListener(new StergereRandAbonat());
		} catch (SQLException ex) {
			// JOptionPane.showMessageDialog(null, "Eroare: "+ex.getMessage());
			throw new IllegalStateException(ex);
		}
	}

	// Metoda pentru activarea casutelor de input la selectarea prin click
	// a unui rand din tabel
	void selecteazaRand() {
		activareInput();
		try {
			int rand = getRandSelectat();
			String sql = "select * from abonat where id="
					+ tabelPopulat.getValueAt(rand, 0);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				numeText.setText(rs.getString("nume"));
				prenumeText.setText(rs.getString("prenume"));
				cnpText.setText(rs.getString("cnp"));
				telefonText.setText(rs.getString("telefon"));
			}

			randSelectat = rand;
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
		}

	}

	private int getRandSelectat() {
		int rand = tabelPopulat.getSelectedRow();

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
		cautaAbonat.addActionListener(new CautaAbonat());

		JLabel numeLabel = new JLabel("Nume");
		JLabel prenumeLabel = new JLabel("Prenume");
		JLabel cnpLabel = new JLabel("CNP");
		JLabel telefonLabel = new JLabel("Telefon");

		panouInterfataAdaugare.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTHEAST;

		cautareText.setMinimumSize(cautareText.getPreferredSize());
		numeText.setMinimumSize(cautareText.getPreferredSize());
		prenumeText.setMinimumSize(cautareText.getPreferredSize());
		cnpText.setMinimumSize(cautareText.getPreferredSize());
		telefonText.setMinimumSize(cautareText.getPreferredSize());

		int i = 0;

		gbc.gridx = 0;
		gbc.gridy = i;
		panouInterfataAdaugare.add(cautareLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(cautareText, gbc);

		i++;

		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(numeLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(numeText, gbc);

		i++;

		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(prenumeLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(prenumeText, gbc);

		i++;

		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(cnpLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(cnpText, gbc);

		i++;

		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		panouInterfataAdaugare.add(telefonLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(telefonText, gbc);

		i++;

		gbc.gridx = 1;
		gbc.gridy = i;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panouInterfataAdaugare.add(cautaAbonat, gbc);

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

		adaugaAbonat.addActionListener(new ActivareInput());
		salveazaAbonat.addActionListener(new SalveazaAbonat());
		stergeAbonat.addActionListener(new StergeAbonat());
		actualizeazaAbonat.addActionListener(new ActualizeazaAbonat());
		anuleazaInregistrare.addActionListener(new AnuleazaInput());

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

	/*
	 * Metoda de adaugare abonat Se construieste un obiect nou de tip abonat in
	 * care se verifica daca tipul de numar de telefon introdus este mobil sau
	 * fix si daca nu incepe cu '07' sau '02' si nu are 10 caractere se afiseaza
	 * o eroare
	 */
	private void adaugareAbonat() {

		NrTel numar = null;
		String nume = numeText.getText();
		String prenume = prenumeText.getText();
		String cnp = cnpText.getText();
		String telefon = telefonText.getText();

		if ((nume != null && nume.length() > 0)
				&& (prenume != null && prenume.length() > 0)
				&& (cnp != null && cnp.length() > 0)
				&& (telefon != null && telefon.length() > 0)) {

			try {
				numar = nrTel.getTipNumarTel(telefon);
			} catch (Exception e) {
				JOptionPane
						.showMessageDialog(null, "Eroare: " + e.getMessage());
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
				if (n > 0) {
					actualizareTabel();
					stergereInput();
					JOptionPane.showMessageDialog(null,
							"Date salvate cu succes!");
				}

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null,
						"Eroare: " + ex.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Completati toate campurile!");
		}
	}

	// metoda pentru cautarea abonatului in functie de nume, prenume, cnp sau
	// telefon
	private void cautareAbonat() {
		try {
			String textCautat = cautareText.getText().trim();

			if (textCautat != null & textCautat.length() > 0) {
				String sql = "SELECT nume,prenume,cnp,telefon from abonat "
						+ "where nume like '%" + textCautat + "%' "
						+ "or prenume like '%" + textCautat + "%' "
						+ "or cnp like '%" + textCautat + "%' "
						+ "or telefon like '%" + textCautat + "%'";
				String sqlRezultate = "SELECT COUNT(*) as rezultate FROM ( "
						+ sql + " ) as inregistrari";
				stmt = conn.createStatement();

				int rezultate = 0;
				ResultSet rsRezultate = stmt.executeQuery(sqlRezultate);
				rsRezultate.next();
				rezultate = rsRezultate.getInt(1);
				rsRezultate.close();

				ResultSet rs = stmt.executeQuery(sql);
				if (rezultate == 1) {
					rs.next();
					numeText.setText(rs.getString("nume"));
					prenumeText.setText(rs.getString("prenume"));
					cnpText.setText(rs.getString("cnp"));
					telefonText.setText(rs.getString("telefon"));
				} else if (rezultate > 1) {
					JOptionPane
							.showMessageDialog(
									null,
									"Am gasit : "
											+ rezultate
											+ " rezultate. Cautati dupa telefon sau CNP pentru rezultate unice!");
					rs.next();
					numeText.setText(rs.getString("nume"));
					prenumeText.setText(rs.getString("prenume"));
					cnpText.setText(rs.getString("cnp"));
					telefonText.setText(rs.getString("telefon"));
				} else {
					JOptionPane.showMessageDialog(null,
							"Niciun rezultat gasit!");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Completati campul de cautare!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
		}

	}

	// stergerea abonatului din tabel si baza de date
	private void stergeAbonat() {
		int rand = getRandSelectat();
		try {
			int valoareMesaj = JOptionPane.showConfirmDialog(tabelPopulat,
					"Doriti stergerea abonatului?", "Confirmati stergerea",
					JOptionPane.YES_NO_OPTION);
			if (valoareMesaj == JOptionPane.YES_OPTION) {
				String stergeSQL = "DELETE FROM ABONAT " + " WHERE id="
						+ tabelPopulat.getValueAt(rand, 0);
				stmt = conn.createStatement();
				int n = stmt.executeUpdate(stergeSQL);
				randSelectat = 0;
				
				/*
				 * daca n este mai mare ca 0 inseamna ca s-au facut modificari
				 * in baza si trebuie sa se actualizeze datele afisate in tabel
				 * si sa se curete campurile de input si sa se dezactiveze
				 */
				if (n > 0) {
					stergereInput();
					actualizareTabel();
					dezactivareInput();
					JOptionPane.showMessageDialog(null, "Abonat sters!");
				}
			} else if (valoareMesaj == JOptionPane.NO_OPTION) {
				JOptionPane
						.showMessageDialog(null, "Abonatul nu a fost sters!");
			}

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
		}

	}

	// modificarea datelor unui abonat dupa selectarea/gasirea in tabel
	private void actualizeazaAbonat() {

		String nume = numeText.getText();
		String prenume = prenumeText.getText();
		String cnp = cnpText.getText();
		String telefon = telefonText.getText();

		if ((nume != null && nume.length() > 0)
				&& (prenume != null && prenume.length() > 0)
				&& (cnp != null && cnp.length() > 0)
				&& (telefon != null && telefon.length() > 0)) {

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
				if (n > 0) {
					stergereInput();
					actualizareTabel();
					JOptionPane.showMessageDialog(null,
							"Date actualizate cu succes!");
				}
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null,
						"Eroare: " + ex.getMessage());

			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Cautati sau selectati un abonat!");
		}
	}

	public static void main(String[] args) {
		CarteDeTelefon agenda = new CarteDeTelefon();
		agenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		agenda.setLocationRelativeTo(null);
		agenda.setTitle("Agenda Telefonica");
		agenda.pack();
		agenda.setVisible(true);
	}

}
