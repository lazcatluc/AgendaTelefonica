package agenda;

public abstract class NrTel {
	private String numar;

	public NrTel(String numar) {
		this.numar = numar;
	}
	public String getNumar() {
		return numar;
	}

	public abstract boolean verificareTipNumar(String numar);
	
	public void setNumar(String numar) {
		this.numar = numar;
	}
	@Override
	public String toString() {
		return "NrTel [numar=" + numar + "]";
	}
	
	
}
