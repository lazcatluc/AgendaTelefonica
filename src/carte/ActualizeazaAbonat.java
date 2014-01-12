package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import agenda.CarteDeTelefon;

public class ActualizeazaAbonat extends AbstractCarteDeTelefonActionListener
		implements ActionListener {

	public ActualizeazaAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String nume = getCarteDeTelefon().getNumeText().getText();
		String prenume = getCarteDeTelefon().getPrenumeText().getText();
		String cnp = getCarteDeTelefon().getCnpText().getText();
		String telefon = getCarteDeTelefon().getTelefonText().getText();

		if ((nume != null && nume.length() > 0)
				&& (prenume != null && prenume.length() > 0)
				&& (cnp != null && cnp.length() > 0)
				&& (telefon != null && telefon.length() > 0)) {

			try {
				String adaugaQuery = "UPDATE ABONAT SET nume=?, prenume=?, cnp=? ,telefon=?"
						+ " WHERE id=?";
				int rand = getCarteDeTelefon().getTabelPopulat().getSelectedRow();
				String id = (String) getCarteDeTelefon().getTabelPopulat().getValueAt(rand, 0);
				PreparedStatement ps = getCarteDeTelefon().getConn().prepareStatement(adaugaQuery);
				ps.setString(1, getCarteDeTelefon().getNumeText().getText());
				ps.setString(2, getCarteDeTelefon().getPrenumeText().getText());
				ps.setString(3, getCarteDeTelefon().getCnpText().getText());
				ps.setString(4, getCarteDeTelefon().getTelefonText().getText());
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

}
