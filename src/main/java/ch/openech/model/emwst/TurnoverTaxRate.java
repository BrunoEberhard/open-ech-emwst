package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

public class TurnoverTaxRate {

	@Size(5) @Decimal(2) @NotEmpty
	public BigDecimal taxRate;
	
	@Decimal(2) @NotEmpty
	public BigDecimal turnover;
}
