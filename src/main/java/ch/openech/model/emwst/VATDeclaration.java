package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;

public class VATDeclaration {
	public static final VATDeclaration $ = Keys.of(VATDeclaration.class);
	
	public Object id;
	
	public final GeneralInformation generalInformation = new GeneralInformation();
	public final TurnoverComputation turnoverComputation = new TurnoverComputation();
	
	public EffectiveReportingMethod effectiveReportingMethod;
	public NetTaxRateMethod netTaxRateMethod;
	public FlatTaxRateMethod flatTaxRateMethod;
	
	@NotEmpty @Decimal(2)
	public BigDecimal payableTax;
	
	public OtherFlowsOfFunds otherFlowsOfFunds;

	public VATDeclaration() {
		//
	}
	
	public VATDeclaration(Class<?> reportingMethod) {
		if (reportingMethod == FlatTaxRateMethod.class) {
			flatTaxRateMethod = new FlatTaxRateMethod();
		} else if (reportingMethod == EffectiveReportingMethod.class) {
			effectiveReportingMethod = new EffectiveReportingMethod();
		} else if (reportingMethod == NetTaxRateMethod.class) {
			netTaxRateMethod = new NetTaxRateMethod();
		} else {
			throw new IllegalArgumentException(reportingMethod.getName());
		}
	}
	
	public Object getReportingMethod() {
		if (effectiveReportingMethod != null) {
			return effectiveReportingMethod;
		} else if (flatTaxRateMethod != null) {
			return flatTaxRateMethod;
		} else if (netTaxRateMethod != null) {
			return netTaxRateMethod;
		} else {
			return null;
		}
	}
}
