package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import agenda.CarteDeTelefon;

public class AnuleazaInput extends AbstractCarteDeTelefonActionListener
		implements ActionListener {

	public AnuleazaInput(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stergereInput();
		dezactivareInput();
	}

}
