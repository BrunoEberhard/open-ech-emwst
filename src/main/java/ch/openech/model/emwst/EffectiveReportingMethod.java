package ch.openech.model.emwst;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

public class EffectiveReportingMethod {
	public static final EffectiveReportingMethod $ = Keys.of(EffectiveReportingMethod.class);

	public Object id;

	@NotEmpty
	public GrossOrNet grossOrNet;
	@Size(18) @Decimal(2)
	public BigDecimal opted;
	
	public final List<TurnoverTaxRate> suppliesPerTaxRate = new ArrayList<>();
	public final List<TurnoverTaxRate> acquisitionTax = new ArrayList<>();
	
	@Size(18) @Decimal(2)
	public BigDecimal inputTaxMaterialAndServices,
		inputTaxInvestments,
		subsequentInputTaxDeduction,
		inputTaxCorrections,
		inputTaxReductions;

}
