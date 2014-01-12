package carte;

import java.awt.event.ActionEvent;

import agenda.CarteDeTelefon;

public class CautaAbonat extends AbstractCarteDeTelefonActionListener {

	public CautaAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getCarteDeTelefon().cautareAbonat();
	}

}
