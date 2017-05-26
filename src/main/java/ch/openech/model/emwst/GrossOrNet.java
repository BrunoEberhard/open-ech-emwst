package ch.openech.model.emwst;

import ch.openech.model.types.EchCode;

public enum GrossOrNet implements EchCode {
	Netto, Brutto;
	
	@Override
	public String getValue() {
		return String.valueOf(this.ordinal() + 1);
	}
}
