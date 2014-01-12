package agenda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ActualizareAgendaTest {
	
	private ResultSet resultSet;
	private CarteDeTelefon carteDeTelefon;
	
	@Before
	public void setUp() throws Exception {
		Connection conn = mock(Connection.class);
		Statement statement = mock(Statement.class);
		when(conn.createStatement()).thenReturn(statement);
		resultSet = mock(ResultSet.class);
		when(statement.executeQuery(anyString())).thenReturn(resultSet);
		carteDeTelefon = new CarteDeTelefon(conn);
	}
	
	@Test
	public void actualizareTabel() throws Exception {
		when(resultSet.next()).thenReturn(true).thenReturn(false);
		carteDeTelefon.actualizareTabel();
	}
}
