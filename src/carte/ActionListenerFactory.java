package carte;

import agenda.CarteDeTelefon;

public class ActionListenerFactory {
	private final CarteDeTelefon carteDeTelefon;
	
	public ActionListenerFactory(CarteDeTelefon carteDeTelefon) {
		this.carteDeTelefon = carteDeTelefon;
	}
	
	public ActivateInput getActivateInput() {
		return new ActivateInput(carteDeTelefon);
	}
	
	public SalveazaAbonat getSalveazaAbonat() {
		return new SalveazaAbonat(carteDeTelefon);
	}
	
	public StergeAbonat getStergeAbonat() {
		return new StergeAbonat(carteDeTelefon);
	}
	
	public ActualizeazaAbonat getActualizeazaAbonat() {
		return new ActualizeazaAbonat(carteDeTelefon);
	}
	
	public AnuleazaInput getAnuleazaInput() {
		return new AnuleazaInput(carteDeTelefon);
	}
	
	public CautaAbonat getCautaAbonat() {
		return new CautaAbonat(carteDeTelefon);
	}
	
	public SelectieTabel getSelectieTabel() {
		return new SelectieTabel(carteDeTelefon);
	}
	
	public StergereRandAbonat getStergereRandAbonat() {
		return new StergereRandAbonat(carteDeTelefon);
	}
}

