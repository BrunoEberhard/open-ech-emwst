package ch.openech.model.emwst;

import java.math.BigDecimal;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;

public class VariousDeduction {
	public static final VariousDeduction $ = Keys.of(VariousDeduction.class);

	@NotEmpty @Size(18) @Decimal(2)
	public BigDecimal amountVariousDeduction;
	@Size(50) @NotEmpty
	public String descriptionVariousDeduction;

	
}
