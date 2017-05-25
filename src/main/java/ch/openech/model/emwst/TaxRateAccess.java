package ch.openech.model.emwst;

import java.util.List;

public interface TaxRateAccess {

	public List<ActivityTurnoverTaxRate> getSuppliesPerTaxRate();

	public void setSuppliesPerTaxRate(List<ActivityTurnoverTaxRate> rates);

	public List<TurnoverTaxRate> getAcquisitionTax();

	public void setAcquisitionTax(List<TurnoverTaxRate> rates);

}
