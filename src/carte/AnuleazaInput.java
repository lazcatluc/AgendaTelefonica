package carte;

import java.awt.event.ActionEvent;

import agenda.CarteDeTelefon;

public class AnuleazaInput extends AbstractCarteDeTelefonActionListener {

	public AnuleazaInput(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getCarteDeTelefon().stergereInput();
		getCarteDeTelefon().dezactivareInput();
	}

}
