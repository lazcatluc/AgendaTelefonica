package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import agenda.CarteDeTelefon;

public class StergeAbonat extends AbstractCarteDeTelefonActionListener 
	implements ActionListener {

	public StergeAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stergeAbonat();
	}

	public void stergeAbonat() {
		int rand = getCarteDeTelefon().getRandSelectat();
		try {
			int valoareMesaj = JOptionPane.showConfirmDialog(getCarteDeTelefon().getTabelPopulat(),
					"Doriti stergerea abonatului?", "Confirmati stergerea",
					JOptionPane.YES_NO_OPTION);
			if (valoareMesaj == JOptionPane.YES_OPTION) {
				String stergeSQL = "DELETE FROM ABONAT " + " WHERE id="
						+ getCarteDeTelefon().getTabelPopulat().getValueAt(rand, 0);
				Statement stmt = getCarteDeTelefon().getConn().createStatement();
				int n = stmt.executeUpdate(stergeSQL);
				getCarteDeTelefon().setRandSelectat(0);
				
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

}
