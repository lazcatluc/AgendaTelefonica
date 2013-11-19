package agenda;

public class NrMobil extends NrTel{

	private String mobil;
	private static final int lungimeNumar = 10;
	private static final String identificareNumar = "07";
	
	public NrMobil(String mobil) {
		super(mobil);
		if(verificareTipNumar(mobil)) {
			this.mobil = mobil;
		} 
		else {
			System.exit(0);
		}
		
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		if(verificareTipNumar(mobil)) {
			this.mobil = mobil;
		} 
		
	}

	@Override
	public String toString() {
		//return "NrMobil [mobil=" + mobil + "]";
		return mobil;
	}

	@Override
	public boolean verificareTipNumar(String numar) {
		if(numar.length() != lungimeNumar) {
			System.out.println("Numarul introdus este incorect!");
			return false;
		}
		else {
			if (numar.startsWith(identificareNumar)) {
				return true;
			}
			else {
				System.out.println("Numarul nu este de mobil!");
				return false;
			}
			
		}
		
	}
	
	
}
