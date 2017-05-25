package ch.openech.model.emwst;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.Size;

public class FlatTaxRateMethod {
	public static final FlatTaxRateMethod $ = Keys.of(FlatTaxRateMethod.class);

	public Object id;

	public final List<ActivityTurnoverTaxRate> suppliesPerTaxRate = new ArrayList<>();
	public final List<TurnoverTaxRate> acquisitionTax = new ArrayList<>();

	@Size(18)
	@Decimal(2)
	public BigDecimal compensationExport, deemedInputTaxDeduction, marginTaxation;

	public CompilationCompensationExport compilationCompensationExport;
	public CompilationDeemedInputTaxDeduction compilationDeemedInputTaxDeduction;
	public CompilationMarginTaxation compilationMarginTaxation;
	
}