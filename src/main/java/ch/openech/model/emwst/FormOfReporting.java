package ch.openech.model.emwst;

import ch.openech.model.types.EchCode;

public enum FormOfReporting implements EchCode {

	vereinbart, vereinnahmt;

	@Override
	public String getValue() {
		return String.valueOf(this.ordinal() + 1);
	}
	
}
