package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

public class TurnoverComputation {
	public static final TurnoverComputation $ = Keys.of(TurnoverComputation.class);

	@NotEmpty @Size(18) @Decimal(2)
	public BigDecimal totalConsideration;
	@Size(18) @Decimal(2)
	public BigDecimal suppliesToForeignCountries, 
		suppliesAbroad,
		transferNotificationProcedure,
		suppliesExemptFromTax,
		reductionOfConsideration;
	
	public VariousDeduction variousDeduction;

	
}
