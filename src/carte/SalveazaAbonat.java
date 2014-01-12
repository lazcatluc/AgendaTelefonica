package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import agenda.Abonat;
import agenda.CarteDeTelefon;
import agenda.NrTel;
import agenda.TipNumarTelefon;

public class SalveazaAbonat extends AbstractCarteDeTelefonActionListener
		implements ActionListener {
	
	private TipNumarTelefon nrTel = new TipNumarTelefon();
	
	public SalveazaAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaugareAbonat();
		stergereInput();
		actualizareTabel();
	}
	
	/*
	 * Metoda de adaugare abonat Se construieste un obiect nou de tip abonat in
	 * care se verifica daca tipul de numar de telefon introdus este mobil sau
	 * fix si daca nu incepe cu '07' sau '02' si nu are 10 caractere se afiseaza
	 * o eroare
	 */
	private void adaugareAbonat() {
		NrTel numar = null;
		String nume = getCarteDeTelefon().getNumeText().getText();
		String prenume = getCarteDeTelefon().getPrenumeText().getText();
		String cnp = getCarteDeTelefon().getCnpText().getText();
		String telefon = getCarteDeTelefon().getTelefonText().getText();

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
				PreparedStatement ps = getCarteDeTelefon().getConn().prepareStatement(adaugaQuery);
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

}
