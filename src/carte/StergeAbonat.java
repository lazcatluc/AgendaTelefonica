package carte;

import java.awt.event.ActionEvent;

import agenda.CarteDeTelefon;

public class StergeAbonat extends AbstractCarteDeTelefonActionListener {

	public StergeAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getCarteDeTelefon().stergeAbonat();
	}

}
