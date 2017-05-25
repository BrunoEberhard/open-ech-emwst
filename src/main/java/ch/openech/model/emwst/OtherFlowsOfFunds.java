package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.annotation.Decimal;

public class OtherFlowsOfFunds {

	@Decimal(2)
	public BigDecimal subsidies, donations;
}
