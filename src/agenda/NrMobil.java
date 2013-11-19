package agenda;

public class NrMobil extends NrTel{

	private String mobil;
	
	public NrMobil(String mobil) {
		super(mobil);
		this.mobil = mobil;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	@Override
	public String toString() {
		//return "NrMobil [mobil=" + mobil + "]";
		return mobil;
	}

}
