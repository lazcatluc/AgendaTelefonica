package agenda;

public class NrFix extends NrTel {
	
	private String fix;

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

}
