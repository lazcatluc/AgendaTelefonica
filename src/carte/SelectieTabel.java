package carte;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agenda.CarteDeTelefon;

/*
 * Clasa pentru selectarea randului din tabel care implementeaza
 * ListSelectionListener pentru completarea datelor in campurile de input
 * dupa selectarea unui rand
 */

public class SelectieTabel extends AbstractCarteDeTelefonActionListener implements ListSelectionListener {

	public SelectieTabel(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	// Metoda pentru activarea casutelor de input la selectarea prin click
	// a unui rand din tabel
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			activareInput();
			try {
				int rand = getCarteDeTelefon().getRandSelectat();
				String sql = "select * from abonat where id="
						+ getCarteDeTelefon().getTabelPopulat().getValueAt(rand, 0);
				Statement stmt = getCarteDeTelefon().getConn().createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					getCarteDeTelefon().getNumeText().setText(rs.getString("nume"));
					getCarteDeTelefon().getPrenumeText().setText(rs.getString("prenume"));
					getCarteDeTelefon().getCnpText().setText(rs.getString("cnp"));
					getCarteDeTelefon().getTelefonText().setText(rs.getString("telefon"));
				}

				getCarteDeTelefon().setRandSelectat(rand);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
			}
		}
	}

}
