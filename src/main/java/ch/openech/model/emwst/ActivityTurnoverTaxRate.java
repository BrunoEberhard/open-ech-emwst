package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

public class ActivityTurnoverTaxRate {

	@Size(255) @NotEmpty
	public String activity;
	
	@Size(5) @Decimal(2) @NotEmpty
	public BigDecimal taxRate;
	
	@Decimal(2) @NotEmpty
	public BigDecimal turnover;
}
