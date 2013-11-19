package agenda;

public class TipNumarTelefon {
	
	private static final int lungimeNumar = 10;
	private static final String identificareNumarFix = "02";
	private static final String identificareNumarMobil = "07";

	public NrTel getTipNumarTel(String numarTel) {
		if((numarTel==null) || (numarTel.length() != lungimeNumar)) {
			System.out.println("ERR: Formatul numarlui introdus este incorect!");
			return null;
		}
		
		
		if(numarTel.startsWith(identificareNumarMobil)){
			return new NrMobil(numarTel);
		} else if(numarTel.startsWith(identificareNumarFix)){
			return new NrFix(numarTel);
		} else {
			System.out.println("ERR: Nu ati introdus un numar de fix sau de mobil!");
			return null;
		}
	}
}
