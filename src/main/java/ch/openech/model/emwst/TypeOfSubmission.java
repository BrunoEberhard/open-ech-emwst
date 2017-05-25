package ch.openech.model.emwst;

import ch.openech.model.types.EchCode;

public enum TypeOfSubmission implements EchCode {

	Ersteinreichung, Korrekturabrechnung, Jahresabstimmung;
	
	@Override
	public String getValue() {
		return String.valueOf(this.ordinal() + 1);
	}
	
}
