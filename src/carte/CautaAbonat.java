package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import agenda.CarteDeTelefon;

public class CautaAbonat extends AbstractCarteDeTelefonActionListener implements
		ActionListener {

	public CautaAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String textCautat = getCarteDeTelefon().getCautareText().getText().trim();

			if (textCautat != null & textCautat.length() > 0) {
				String sql = "SELECT nume,prenume,cnp,telefon from abonat "
						+ "where nume like '%" + textCautat + "%' "
						+ "or prenume like '%" + textCautat + "%' "
						+ "or cnp like '%" + textCautat + "%' "
						+ "or telefon like '%" + textCautat + "%'";
				String sqlRezultate = "SELECT COUNT(*) as rezultate FROM ( "
						+ sql + " ) as inregistrari";
				Statement stmt = getCarteDeTelefon().getConn().createStatement();

				int rezultate = 0;
				ResultSet rsRezultate = stmt.executeQuery(sqlRezultate);
				rsRezultate.next();
				rezultate = rsRezultate.getInt(1);
				rsRezultate.close();

				ResultSet rs = stmt.executeQuery(sql);
				if (rezultate == 1) {
					rs.next();
					getCarteDeTelefon().getNumeText().setText(rs.getString("nume"));
					getCarteDeTelefon().getPrenumeText().setText(rs.getString("prenume"));
					getCarteDeTelefon().getCnpText().setText(rs.getString("cnp"));
					getCarteDeTelefon().getTelefonText().setText(rs.getString("telefon"));
				} else if (rezultate > 1) {
					JOptionPane
							.showMessageDialog(
									null,
									"Am gasit : "
											+ rezultate
											+ " rezultate. Cautati dupa telefon sau CNP pentru rezultate unice!");
					rs.next();
					getCarteDeTelefon().getNumeText().setText(rs.getString("nume"));
					getCarteDeTelefon().getPrenumeText().setText(rs.getString("prenume"));
					getCarteDeTelefon().getCnpText().setText(rs.getString("cnp"));
					getCarteDeTelefon().getTelefonText().setText(rs.getString("telefon"));
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

}
