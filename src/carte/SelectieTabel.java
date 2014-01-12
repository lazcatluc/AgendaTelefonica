package carte;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agenda.CarteDeTelefon;

/*
 * Clasa pentru selectarea randului din tabel care implementeaza
 * ListSelectionListener pentru completarea datelor in campurile de input
 * dupa selectarea unui rand
 */

public class SelectieTabel implements ListSelectionListener {
	
	private final CarteDeTelefon carteDeTelefon;

	public SelectieTabel(CarteDeTelefon carteDeTelefon) {
		super();
		this.carteDeTelefon = carteDeTelefon;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			carteDeTelefon.selecteazaRand();
		}
	}

}
