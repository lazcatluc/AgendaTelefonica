/*
 * Clasa de construire a obiectelor de tip NrTel
 * Verificare tipul de numar intors si intoarcerea
 * unui obiect corespunzator de tip NrMobil
 * sau de tip NrFix
 */
package agenda;

public class TipNumarTelefon {
	
	private static final int lungimeNumar = 10;
	private static final String identificareNumarFix = "02";
	private static final String identificareNumarMobil = "07";

	public NrTel getTipNumarTel(String numarTel) throws Exception {
		if((numarTel==null) || (numarTel.length() != lungimeNumar)) {
			throw new Exception("Formatul numarlui introdus este incorect!");
		}
		
		
		if(numarTel.startsWith(identificareNumarMobil)){
			return new NrMobil(numarTel);
		} else if(numarTel.startsWith(identificareNumarFix)){
			return new NrFix(numarTel);
		} else {
			throw new Exception("Formatul numarului introdus este incorect!");
		}
	}
}
