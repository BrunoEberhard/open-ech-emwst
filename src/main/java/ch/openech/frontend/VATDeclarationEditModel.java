package ch.openech.frontend;

import java.math.BigDecimal;
import java.util.List;

import org.minimalj.model.Keys;
import org.minimalj.model.annotation.Decimal;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;
import org.minimalj.model.properties.Properties;
import org.minimalj.model.properties.PropertyInterface;
import org.minimalj.util.CloneHelper;
import org.minimalj.util.StringUtils;

import ch.openech.model.emwst.CompilationCompensationExport;
import ch.openech.model.emwst.CompilationDeemedInputTaxDeduction;
import ch.openech.model.emwst.CompilationMarginTaxation;
import ch.openech.model.emwst.EffectiveReportingMethod;
import ch.openech.model.emwst.FlatTaxRateMethod;
import ch.openech.model.emwst.GeneralInformation;
import ch.openech.model.emwst.GrossOrNet;
import ch.openech.model.emwst.NetTaxRateMethod;
import ch.openech.model.emwst.OtherFlowsOfFunds;
import ch.openech.model.emwst.TurnoverComputation;
import ch.openech.model.emwst.TurnoverTaxRate;
import ch.openech.model.emwst.VATDeclaration;

public class VATDeclarationEditModel {
	public static final VATDeclarationEditModel $ = Keys.of(VATDeclarationEditModel.class);
	
	public Object id;
	private final Class<?> clazz;
	
	public final GeneralInformation generalInformation = new GeneralInformation();
	public final TurnoverComputation turnoverComputation = new TurnoverComputation();
	
	// effective

	@NotEmpty
	public GrossOrNet grossOrNet;
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

		convertToEditModel(method, "suppliesPerTaxRate", true);
		convertToEditModel(method, "acquisitionTax", false);
		
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

	private void convertToEditModel(Object reportingMethod, String fieldName, boolean activity) {
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

	private void convertToModel(Object reportingMethod, String fieldName, boolean activity) {
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
		VATDeclaration vatDeclaration = new VATDeclaration();
		
		if (clazz == FlatTaxRateMethod.class) {
			vatDeclaration.flatTaxRateMethod = new FlatTaxRateMethod();
		} else if (clazz == EffectiveReportingMethod.class) {
			vatDeclaration.effectiveReportingMethod = new EffectiveReportingMethod();
		} else if (clazz == NetTaxRateMethod.class) {
			vatDeclaration.netTaxRateMethod = new NetTaxRateMethod();
		}
		Object method = vatDeclaration.getReportingMethod();

		convertToModel(method, "suppliesPerTaxRate", true);
		convertToModel(method, "acquisitionTax", false);
		
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

}