package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {

	private static String db="agenda";
	private static String user="agenda";
	private static String pass="agenda1";
	private static String url="jdbc:mysql://localhost:3306/"+db;
	private static Connection Conn;
	
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Conn = DriverManager.getConnection(url,user,pass);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Conn;
	}
}
