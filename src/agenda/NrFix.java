package agenda;

public class NrFix extends NrTel {
	
	private String fix;
	private static final int lungimeNumar = 10;
	private static final String identificareNumar = "02";

	public NrFix(String fix) {
		super(fix);
		this.fix = fix;
	}

	public String getFix() {
		return fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}
	

	@Override
	public String toString() {
		return fix;
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
				System.out.println("Numarul nu este de fix!");
				return false;
			}
			
		}
	}

}
