package agenda;

import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionEvent;

import org.junit.Before;
import org.junit.Test;

import database.MySQL;

public class ActualizareAgendaIntegrationTest {
	private CarteDeTelefon carteDeTelefon;
	
	@Before
	public void setUp() throws Exception {
		carteDeTelefon = new CarteDeTelefon(MySQL.getConnection());
	}
	
	@Test
	public void actualizareTabel() throws Exception {
		carteDeTelefon.actualizareTabel();
	}
	
	@Test
	public void stergereAbonat() throws Exception {
		carteDeTelefon.actualizareTabel();
		carteDeTelefon.new SelectieTabel().valueChanged(mock(ListSelectionEvent.class));
		carteDeTelefon.new StergeAbonat().actionPerformed(mock(ActionEvent.class));
	}
}
