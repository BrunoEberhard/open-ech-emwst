package ch.openech.model.emwst;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.Size;

public class VerificationCompensationExport {
	public static VerificationCompensationExport $ = Keys.of(VerificationCompensationExport.class);
	
	@Size(255)
	public String invoiceNumber;
	public LocalDate invoiceDate;
	@Size(255)
	public String typeOfService;
	@Size(5) @Decimal(2)
	public String taxRate;
	@Size(18) @Decimal(2)
	public BigDecimal turnover;
}
