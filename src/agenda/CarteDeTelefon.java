package agenda;

import database.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CarteDeTelefon {
	
	private ArrayList<Abonat> abonat = new ArrayList<Abonat>();
	Scanner input = new Scanner(System.in);
	String optiuneMeniu;
	Connection conn;
	Statement sent;
	
	public CarteDeTelefon() {
		conn = MySQL.getConnection();
		do {
			System.out.println("1. Adaugare abonat");
			System.out.println("2. Cauta abonat");
			System.out.println("3. Sterge abonat");
			System.out.println("4. Modifcare abonat");
			System.out.println("5. Afisare abonati");
			System.out.println("6. Iesire");
			
			optiuneMeniu = input.nextLine();
			
			if (optiuneMeniu.equals("1")) {
				adaugareAbonat();
			} else if (optiuneMeniu.equals("2")) {
				cautareAbonat();
			} else if (optiuneMeniu.equals("3")) {
				stergeAbonat();
			} else if (optiuneMeniu.equals("4")) {
				modificaAbonat();
			} else if (optiuneMeniu.equals("5")) {
				afisareAbonati();
			}
		} while(optiuneMeniu.equals("6") ==  false);
	input.close();
		
	}
	
	
	private void adaugareAbonat() {
		Scanner inputAdaugare = new Scanner(System.in);
		String optiuneAdaugare;
		String nume;
		String prenume;
		String cnp;
		String telefon;
		
		do {
			System.out.println("Nume Prenume CNP Telefon");
			optiuneAdaugare = inputAdaugare.nextLine();
			String[] split = optiuneAdaugare.split(" ");
			
			nume=split[0];
			prenume=split[1];
			cnp=split[2];
			telefon=split[3];
			
			Abonat abonatUnic = new Abonat(nume, prenume, cnp, new NrMobil(telefon));  
			abonat.add(abonatUnic);
				

			
			
		} while(optiuneAdaugare.equals("x") == false);
		inputAdaugare.close();
		
		Iterator<Abonat> inregistareAbonat = abonat.iterator();
		
		while(inregistareAbonat.hasNext()) {
			Abonat abonat = inregistareAbonat.next();
			try {
				String adaugaQuery = "INSERT INTO ABONAT(nume,prenume,cnp,telefon)"
						+ "VALUES(?,?,?,?)";
				
				PreparedStatement ps = conn.prepareStatement(adaugaQuery);
				ps.setString(1, abonat.getNume());
				ps.setString(2, abonat.getPrenume());
				ps.setString(3, abonat.getCnp());
				ps.setString(4, abonat.getNumarTelefon().toString());
				
				int n = ps.executeUpdate();
				if(n>0) {
					System.out.println("Datele au fost adaugate cu succes!");
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
/*		try {
			File file = new File("D:\\NRS\\AgendaTelefonica\\agenda.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(abonat.toString());
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}*/

	}
	
	private void cautareAbonat() {
		//TODO
	}
	
	private void stergeAbonat() {
		//TODO
	}
	
	private void modificaAbonat() {
		//TODO
	}
	
	private void afisareAbonati(){
		try {
			String afiseazaQuery = "SELECT * FROM ABONAT";
			sent = conn.createStatement();
			ResultSet rs = sent.executeQuery(afiseazaQuery);
			String[] abonat = new String[5];
			System.out.println("ID Nume Prenume CNP Telefon");
			while(rs.next()) {
				abonat[0] = rs.getString("id");
				abonat[1] = rs.getString("nume");
				abonat[2] = rs.getString("prenume");
				abonat[3] = rs.getString("cnp");
				abonat[4] = rs.getString("telefon");
				System.out.println(abonat[0] + " " + abonat[1] + " " + abonat[2] + " " + abonat[3] + " " + abonat[4]);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		Iterator<Abonat> inregistareAbonat = abonat.iterator();
		while(inregistareAbonat.hasNext()) {
			System.out.println(inregistareAbonat.next().toString());
		}
	}
	
	public static void main(String[] args) {
			new CarteDeTelefon();
	}
	
}
