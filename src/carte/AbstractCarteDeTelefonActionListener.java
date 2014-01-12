package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import agenda.CarteDeTelefon;

public abstract class AbstractCarteDeTelefonActionListener implements
		ActionListener {
	
	private final CarteDeTelefon carteDeTelefon;
	
	public AbstractCarteDeTelefonActionListener(CarteDeTelefon carteDeTelefon) {
		this.carteDeTelefon = carteDeTelefon;
	}

	protected CarteDeTelefon getCarteDeTelefon() {
		return carteDeTelefon;
	}

}
