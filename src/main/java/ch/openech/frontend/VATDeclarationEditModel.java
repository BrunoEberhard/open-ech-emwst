package ch.openech.frontend;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;
import org.minimalj.model.properties.Properties;
import org.minimalj.model.properties.PropertyInterface;
import org.minimalj.util.CloneHelper;
import org.minimalj.util.StringUtils;
import org.minimalj.util.mock.Mocking;

import ch.openech.model.emwst.CompilationCompensationExport;
import ch.openech.model.emwst.CompilationDeemedInputTaxDeduction;
import ch.openech.model.emwst.CompilationMarginTaxation;
import ch.openech.model.emwst.EffectiveReportingMethod;
import ch.openech.model.emwst.FormOfReporting;
import ch.openech.model.emwst.GeneralInformation;
import ch.openech.model.emwst.GrossOrNet;
import ch.openech.model.emwst.OtherFlowsOfFunds;
import ch.openech.model.emwst.TurnoverComputation;
import ch.openech.model.emwst.TurnoverTaxRate;
import ch.openech.model.emwst.TypeOfSubmission;
import ch.openech.model.emwst.VATDeclaration;

public class VATDeclarationEditModel implements Mocking {
	public static final VATDeclarationEditModel $ = Keys.of(VATDeclarationEditModel.class);
	
	public Object id;
	private final Class<?> clazz;
	
	public final GeneralInformation generalInformation = new GeneralInformation();
	public final TurnoverComputation turnoverComputation = new TurnoverComputation();
	
	// effective

	@NotEmpty
	public GrossOrNet grossOrNet = GrossOrNet.Brutto;
	@Size(18) @Decimal(2)
	public BigDecimal opted;
	
	//
	@Size(18) @Decimal(2)
	public BigDecimal inputTaxMaterialAndServices,
		inputTaxInvestments,
		subsequentInputTaxDeduction,
		inputTaxCorrections,
		inputTaxReductions;
	
	// flat or net tax rate
	
	@Size(18)
	@Decimal(2)
	public BigDecimal compensationExport, deemedInputTaxDeduction, marginTaxation;

	public CompilationCompensationExport compilationCompensationExport;
	public CompilationDeemedInputTaxDeduction compilationDeemedInputTaxDeduction;
	public CompilationMarginTaxation compilationMarginTaxation;
	
	// all
	
	public TurnoverTaxRate suppliesPerTaxRate0 = new TurnoverTaxRate();
	public TurnoverTaxRate suppliesPerTaxRate1 = new TurnoverTaxRate();
	public TurnoverTaxRate suppliesPerTaxRate2 = new TurnoverTaxRate();
	
	public TurnoverTaxRate acquisitionTax0 = new TurnoverTaxRate();
	public TurnoverTaxRate acquisitionTax1 = new TurnoverTaxRate();
	public TurnoverTaxRate acquisitionTax2 = new TurnoverTaxRate();
	
	@NotEmpty @Decimal(2)
	public BigDecimal payableTax;
	
	public OtherFlowsOfFunds otherFlowsOfFunds;
	
	//

	public VATDeclarationEditModel() {
		this.clazz = null;
	}
	
	public VATDeclarationEditModel(VATDeclaration declaration) {
		Object method = declaration.getReportingMethod();
		this.clazz = method.getClass();

		convertToEditModel(method, "suppliesPerTaxRate");
		convertToEditModel(method, "acquisitionTax");
		
		for (PropertyInterface myProperty : Properties.getProperties(this.getClass()).values()) {
			for (PropertyInterface methodProperty : Properties.getProperties(method.getClass()).values()) {
				if (StringUtils.equals(myProperty.getPath(), methodProperty.getPath())) {
					myProperty.setValue(this, methodProperty.getValue(method));
				}
			}
		}
		
		payableTax = declaration.payableTax;
		otherFlowsOfFunds = declaration.otherFlowsOfFunds;
		
		CloneHelper.deepCopy(declaration.generalInformation, this.generalInformation);
		CloneHelper.deepCopy(declaration.turnoverComputation, this.turnoverComputation);
	}

	private void convertToEditModel(Object reportingMethod, String fieldName) {
		List list = (List) Properties.getProperty(reportingMethod.getClass(), fieldName).getValue(reportingMethod);
		int i = 0;
		for (Object o : list) {
			if (o == null) {
				continue;
			}
			PropertyInterface editModelProperty = Properties.getProperty(this.getClass(), fieldName + i);
			editModelProperty.setValue(this, o);
			i++;
		}
	}

	private void convertToModel(Object reportingMethod, String fieldName) {
		List list = (List) Properties.getProperty(reportingMethod.getClass(), fieldName).getValue(reportingMethod);
		list.clear();
		for (int i = 0; i<3; i++) {
			PropertyInterface editModelProperty = Properties.getProperty(this.getClass(), fieldName + i);
			Object o = editModelProperty.getValue(this);
			if (o == null) {
				continue;
			}
			list.add(o);
		}
	}

	public VATDeclarationEditModel(Class<?> clazz) {
		this.clazz = clazz;
	}

	// EditModel -> Model
	public VATDeclaration convertToModel() {
		VATDeclaration vatDeclaration = new VATDeclaration(clazz);

		Object method = vatDeclaration.getReportingMethod();
		convertToModel(method, "suppliesPerTaxRate");
		convertToModel(method, "acquisitionTax");
		
		for (PropertyInterface myProperty : Properties.getProperties(this.getClass()).values()) {
			for (PropertyInterface methodProperty : Properties.getProperties(method.getClass()).values()) {
				if (StringUtils.equals(myProperty.getPath(), methodProperty.getPath())) {
					methodProperty.setValue(method, myProperty.getValue(this));
				}
			}
		}

		vatDeclaration.payableTax = this.payableTax;
		vatDeclaration.otherFlowsOfFunds = this.otherFlowsOfFunds;
		
		CloneHelper.deepCopy(this.generalInformation, vatDeclaration.generalInformation);
		CloneHelper.deepCopy(this.turnoverComputation, vatDeclaration.turnoverComputation);
		
		return vatDeclaration;
	}

	@Override
	public void mock() {
		setDefaultRates();

		generalInformation.organisationName = "Demo AG";
		generalInformation.businessReferenceId = "Open Ech Demo";
		
		generalInformation.sendingApplication.manufacturer = "Open Ech";
		generalInformation.sendingApplication.product = "Open Ech - E-MWST";
		generalInformation.sendingApplication.productVersion = "1.0.0";
		generalInformation.generationTime = LocalDateTime.now();
		
		generalInformation.uid.mock();
		
		generalInformation.reportingPeriodFrom = LocalDate.now().withDayOfYear(1);
		generalInformation.reportingPeriodTill = LocalDate.now().withMonth(12).withDayOfMonth(31);
		generalInformation.formOfReporting = FormOfReporting.vereinnahmt;
		generalInformation.typeOfSubmission = TypeOfSubmission.Ersteinreichung;
	
		turnoverComputation.totalConsideration = BigDecimal.valueOf(200 * 1000);
		suppliesPerTaxRate0.turnover = BigDecimal.valueOf(200 * 1000);
		suppliesPerTaxRate1.turnover = BigDecimal.ZERO;
		suppliesPerTaxRate2.turnover = BigDecimal.ZERO;
		
		acquisitionTax0.taxRate = BigDecimal.ZERO;
		acquisitionTax0.turnover = BigDecimal.ZERO;
		
		payableTax = suppliesPerTaxRate0.turnover.multiply(suppliesPerTaxRate0.taxRate).divide(BigDecimal.valueOf(100));
	}
	
	public void setDefaultRates() {
		if (clazz == EffectiveReportingMethod.class) {
			suppliesPerTaxRate0.taxRate = BigDecimal.valueOf(8.0);
			suppliesPerTaxRate1.taxRate = BigDecimal.valueOf(2.5);
			suppliesPerTaxRate2.taxRate = BigDecimal.valueOf(3.8);
		}
	}
	
}