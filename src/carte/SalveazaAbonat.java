package carte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import agenda.CarteDeTelefon;

public class SalveazaAbonat extends AbstractCarteDeTelefonActionListener {
	
	public SalveazaAbonat(CarteDeTelefon carteDeTelefon) {
		super(carteDeTelefon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getCarteDeTelefon().adaugareAbonat();
		getCarteDeTelefon().stergereInput();
		getCarteDeTelefon().actualizareTabel();
	}

}
