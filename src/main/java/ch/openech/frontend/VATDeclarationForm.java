package ch.openech.frontend;

import static ch.openech.frontend.VATDeclarationEditModel.$;

import org.minimalj.frontend.form.Form;
import org.minimalj.util.resources.Resources;

import ch.openech.model.emwst.EffectiveReportingMethod;
import ch.openech.model.emwst.FlatTaxRateMethod;
import ch.openech.model.emwst.GeneralInformation;
import ch.openech.model.emwst.SendingApplication;
import ch.openech.model.emwst.TurnoverComputation;

public class VATDeclarationForm extends Form<VATDeclarationEditModel> {

	public VATDeclarationForm(boolean editable, Class<?> reportingMethod) {
		super(editable, 4);

		addTitle(Resources.getString(GeneralInformation.class.getSimpleName()));
		line($.generalInformation.uid.value, $.generalInformation.organisationName);
		line($.generalInformation.generationTime);
		line($.generalInformation.reportingPeriodFrom, $.generalInformation.reportingPeriodTill);
		line($.generalInformation.typeOfSubmission, $.generalInformation.formOfReporting);
		line($.generalInformation.businessReferenceId);
		SendingApplication $_application = $.generalInformation.sendingApplication;
		line($_application.product, $_application.productVersion, $_application.manufacturer);

		addTitle(Resources.getString(TurnoverComputation.class.getSimpleName()));
		line($.turnoverComputation.totalConsideration);
		line($.turnoverComputation.suppliesToForeignCountries, $.turnoverComputation.suppliesAbroad);
		line($.turnoverComputation.transferNotificationProcedure);
		line($.turnoverComputation.suppliesExemptFromTax);
		line($.turnoverComputation.reductionOfConsideration);
		line($.turnoverComputation.variousDeduction.amountVariousDeduction, $.turnoverComputation.variousDeduction.descriptionVariousDeduction);

		if (reportingMethod == EffectiveReportingMethod.class) {
			line($.grossOrNet);
			line($.opted);
		}
		
		if (reportingMethod == FlatTaxRateMethod.class) {
			line($.suppliesPerTaxRate0.taxRate, $.suppliesPerTaxRate0.turnover, $.suppliesPerTaxRate0.activity);
			line($.suppliesPerTaxRate1.taxRate, $.suppliesPerTaxRate1.turnover, $.suppliesPerTaxRate1.activity);
			line($.suppliesPerTaxRate2.taxRate, $.suppliesPerTaxRate2.turnover, $.suppliesPerTaxRate2.activity);
		} else {
			line($.suppliesPerTaxRate0.taxRate, $.suppliesPerTaxRate0.turnover);
			line($.suppliesPerTaxRate1.taxRate, $.suppliesPerTaxRate1.turnover);
			line($.suppliesPerTaxRate2.taxRate, $.suppliesPerTaxRate2.turnover);
		}

		line($.acquisitionTax0.taxRate, $.acquisitionTax0.turnover);
		line($.acquisitionTax1.taxRate, $.acquisitionTax1.turnover);
		line($.acquisitionTax2.taxRate, $.acquisitionTax2.turnover);

		addTitle("-");
		line($.payableTax);
	}
	
	@Override
	protected int getColumnWidthPercentage() {
		return 125;
	}
}
