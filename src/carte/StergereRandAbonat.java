package carte;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import agenda.CarteDeTelefon;

/*
 * Clasa pentru stergerea abonatului la apasarea tastei DELETEDupa apasarea
 * tastei, utilizatorul este intrebat dacadoreste stergerea
 */
public class StergereRandAbonat implements KeyListener {
	
	private final CarteDeTelefon carteDeTelefon;

	public StergereRandAbonat(CarteDeTelefon carteDeTelefon) {
		this.carteDeTelefon = carteDeTelefon;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_DELETE) {
			int valoareMesaj = JOptionPane.showConfirmDialog(carteDeTelefon.getTabelPopulat(),
					"Doriti stergerea abonatului?", "Confirmati stergerea",
					JOptionPane.YES_NO_OPTION);
			int[] index = carteDeTelefon.getTabelPopulat().getSelectedRows();
			e.consume();
			carteDeTelefon.stergeAbonat();
			if (valoareMesaj == JOptionPane.YES_OPTION) {
				for (int i = index.length - 1; i >= 0; --i) {
					carteDeTelefon.getModel().removeRow(index[i]);
				}
			} else if (valoareMesaj == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(null,
						"Abonatul nu a fost sters!");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
