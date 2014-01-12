package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import agenda.CarteDeTelefon;

public abstract class AbstractCarteDeTelefonActionListener {

	private final CarteDeTelefon carteDeTelefon;

	public AbstractCarteDeTelefonActionListener(CarteDeTelefon carteDeTelefon) {
		this.carteDeTelefon = carteDeTelefon;
	}

	protected CarteDeTelefon getCarteDeTelefon() {
		return carteDeTelefon;
	}

	// metoda de activare a input-urilor de introducere a datelor
	public void activareInput() {
		carteDeTelefon.getNumeText().setEnabled(true);
		carteDeTelefon.getPrenumeText().setEnabled(true);
		carteDeTelefon.getCnpText().setEnabled(true);
		carteDeTelefon.getTelefonText().setEnabled(true);
	}

	// metoda de dezactivare a input-urilor de introducere a datelor
	public void dezactivareInput() {
		carteDeTelefon.getNumeText().setEnabled(false);
		carteDeTelefon.getPrenumeText().setEnabled(false);
		carteDeTelefon.getCnpText().setEnabled(false);
		carteDeTelefon.getTelefonText().setEnabled(false);
	}

	// metoda de stergere a input-urilor de introducere a datelor
	public void stergereInput() {
		carteDeTelefon.getCautareText().setText(null);
		carteDeTelefon.getNumeText().setText(null);
		carteDeTelefon.getPrenumeText().setText(null);
		carteDeTelefon.getCnpText().setText(null);
		carteDeTelefon.getTelefonText().setText(null);
	}

	// metoda pentru actualizarea informatiilor din tabel la schimbarea
	// datelor (adaugare/modificare/stergere)
	public void actualizareTabel() {
		try {
			String[] coloane = { "Nr.#", "Nume", "Prenume", "CNP", "Telefon" };
			String sql = "select * from abonat";
			carteDeTelefon.setModel(new DefaultTableModel(null, coloane));
			Statement stmt = carteDeTelefon.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// folosirea TableRowSorter pentru a ordona datele din tabel prin
			// selectarea unui titlu de coloana
			final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
					carteDeTelefon.getModel());
			carteDeTelefon.getTabelPopulat().setRowSorter(sorter);

			// adaugare listener pentru input-ul de cautare pentru
			// filtrarea datelor din tabel
			carteDeTelefon.getCautareText().getDocument()
					.addDocumentListener(new DocumentListener() {
						private void searchFieldChangedUpdate(DocumentEvent evt) {
							String text = carteDeTelefon.getCautareText()
									.getText();
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
				carteDeTelefon.getModel().addRow(linie);
			}
			carteDeTelefon.getTabelPopulat()
					.setModel(carteDeTelefon.getModel());
			carteDeTelefon.getTabelPopulat().setSelectionMode(
					ListSelectionModel.SINGLE_SELECTION);
			carteDeTelefon.getTabelPopulat().getSelectionModel()
					.addListSelectionListener(carteDeTelefon.getActionListenerFactory().getSelectieTabel());
			carteDeTelefon.getTabelPopulat().addKeyListener(
					carteDeTelefon.getActionListenerFactory().getStergereRandAbonat());
		} catch (SQLException ex) {
			// JOptionPane.showMessageDialog(null, "Eroare: "+ex.getMessage());
			throw new IllegalStateException(ex);
		}
	}


}
