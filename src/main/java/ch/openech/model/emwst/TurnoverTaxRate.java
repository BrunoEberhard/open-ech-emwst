package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

public class TurnoverTaxRate {

	public static final TurnoverTaxRate $ = Keys.of(TurnoverTaxRate.class);
	
	// only used in FlatTaxRateMethod
	@Size(100) @NotEmpty
	public String activity;
	
	@Size(5) @Decimal(2) @NotEmpty
	public BigDecimal taxRate;
	
	@Decimal(2) @NotEmpty
	public BigDecimal turnover;
	
	public String getResult() {
		// In folgender Variante würde definiert, dass result von taxRate und turnover abhängt
		// Vorläufig ist das in der nächten Version von Minimal-J drin, braucht aber noch etwas
		// mehr Research, ob das wirklich eine gute Idee ist.
		// if (Keys.isKeyObject(this)) return Keys.methodOf(this, "result", $.taxRate, $.turnover);

		if (Keys.isKeyObject(this)) return Keys.methodOf(this, "result");
		if (taxRate != null && turnover != null) {
			return taxRate.multiply(turnover).divide(BigDecimal.valueOf(100)).toPlainString();
		} else {
			return null;
		}
	}
}
