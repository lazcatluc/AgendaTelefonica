package carte;

import java.awt.event.ActionEvent;

import agenda.CarteDeTelefon;

public class ActualizeazaAbonat extends AbstractCarteDeTelefonActionListener {

	public ActualizeazaAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getCarteDeTelefon().actualizeazaAbonat();
	}

}
