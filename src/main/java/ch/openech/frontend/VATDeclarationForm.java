package ch.openech.frontend;

import static ch.openech.frontend.VATDeclarationEditModel.$;

import org.minimalj.frontend.form.Form;
import org.minimalj.util.resources.Resources;

import ch.openech.model.SendingApplication;
import ch.openech.model.emwst.EffectiveReportingMethod;
import ch.openech.model.emwst.FlatTaxRateMethod;

public class VATDeclarationForm extends Form<VATDeclarationEditModel> {

	public VATDeclarationForm(boolean editable, Class<?> reportingMethod) {
		super(editable, 4);

		addTitle(Resources.getString("GeneralInformation"));
		if (reportingMethod == EffectiveReportingMethod.class) {
			line($.generalInformation.organisationName, $.generalInformation.uid.value, $.generalInformation.businessReferenceId, $.grossOrNet);
		} else {
			line($.generalInformation.organisationName, $.generalInformation.uid.value, $.generalInformation.businessReferenceId);
		}
		line($.generalInformation.reportingPeriodFrom, $.generalInformation.reportingPeriodTill, $.generalInformation.typeOfSubmission, $.generalInformation.formOfReporting);

		addTitle(Resources.getString("TurnoverComputation"));
		line($.turnoverComputation.totalConsideration);
		line($.turnoverComputation.suppliesAbroad, $.turnoverComputation.suppliesToForeignCountries, $.turnoverComputation.transferNotificationProcedure);
		line($.turnoverComputation.suppliesExemptFromTax,$.turnoverComputation.reductionOfConsideration, $.turnoverComputation.variousDeduction.amountVariousDeduction, $.turnoverComputation.variousDeduction.descriptionVariousDeduction);
		if (reportingMethod == EffectiveReportingMethod.class) {
			line($.opted);
		} else {
			
		}
		
		addTitle("Steuerberechnung");
		if (reportingMethod == FlatTaxRateMethod.class) {
			line($.suppliesPerTaxRate0.taxRate, $.suppliesPerTaxRate0.turnover, $.suppliesPerTaxRate0.activity);
		} else {
			line($.suppliesPerTaxRate0.taxRate, $.suppliesPerTaxRate0.turnover);
			// line($.suppliesPerTaxRate0.taxRate, $.suppliesPerTaxRate0.turnover, new TextFormElement($.suppliesPerTaxRate0.getResult()));
			line($.suppliesPerTaxRate1.taxRate, $.suppliesPerTaxRate1.turnover);
			if (reportingMethod == EffectiveReportingMethod.class) {
				line($.suppliesPerTaxRate2.taxRate, $.suppliesPerTaxRate2.turnover);
			}
		}

		addTitle(Resources.getString("AcquisitionTax"));
		line($.acquisitionTax0.taxRate, $.acquisitionTax0.turnover);

		if (reportingMethod == EffectiveReportingMethod.class) {
			addTitle("Vorsteuern");
			line($.inputTaxMaterialAndServices, $.inputTaxInvestments, $.subsequentInputTaxDeduction);
			addTitle("Vorsteuerkorrekturen/ -kürzungen");
			line($.inputTaxCorrections, $.inputTaxReductions);
		}

		line($.payableTax);

		addTitle("Andere Mittelflüsse (Art. 18 Abs. 2)");
		line($.otherFlowsOfFunds.subsidies, $.otherFlowsOfFunds.donations);
		
		addTitle(Resources.getString("TechnicalInformation"));
		SendingApplication $_application = $.generalInformation.sendingApplication;
		line($_application.product, $_application.productVersion, $_application.manufacturer, $.generalInformation.generationTime);

	}
	
	@Override
	protected int getColumnWidthPercentage() {
		return 125;
	}
}
