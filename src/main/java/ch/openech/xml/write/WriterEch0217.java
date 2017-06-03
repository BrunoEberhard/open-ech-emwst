package ch.openech.xml.write;

import static ch.openech.model.XmlConstants.ACQUISITION_TAX;
import static ch.openech.model.XmlConstants.ACTIVITY;
import static ch.openech.model.XmlConstants.AMOUNT_VARIOUS_DEDUCTION;
import static ch.openech.model.XmlConstants.BUSINESS_REFERENCE_ID;
import static ch.openech.model.XmlConstants.COMPENSATION_EXPORT;
import static ch.openech.model.XmlConstants.DEEMED_INPUT_TAX_DEDUCTION;
import static ch.openech.model.XmlConstants.DESCRIPTION_VARIOUS_DEDUCTION;
import static ch.openech.model.XmlConstants.DONATIONS;
import static ch.openech.model.XmlConstants.FORM_OF_REPORTING;
import static ch.openech.model.XmlConstants.GENERATION_TIME;
import static ch.openech.model.XmlConstants.GROSS_OR_NET;
import static ch.openech.model.XmlConstants.INPUT_TAX_CORRECTIONS;
import static ch.openech.model.XmlConstants.INPUT_TAX_INVESTMENTS;
import static ch.openech.model.XmlConstants.INPUT_TAX_MATERIAL_AND_SERVICES;
import static ch.openech.model.XmlConstants.INPUT_TAX_REDUCTIONS;
import static ch.openech.model.XmlConstants.INVOICE_DATE;
import static ch.openech.model.XmlConstants.INVOICE_NUMBER;
import static ch.openech.model.XmlConstants.MANUFACTURER;
import static ch.openech.model.XmlConstants.MARGIN_TAXATION;
import static ch.openech.model.XmlConstants.OPTED;
import static ch.openech.model.XmlConstants.ORGANISATION_NAME;
import static ch.openech.model.XmlConstants.PAYABLE_TAX;
import static ch.openech.model.XmlConstants.PRODUCT;
import static ch.openech.model.XmlConstants.PRODUCT_VERSION;
import static ch.openech.model.XmlConstants.REDUCTION_OF_CONSIDERATION;
import static ch.openech.model.XmlConstants.REPORTING_PERIOD_FROM;
import static ch.openech.model.XmlConstants.REPORTING_PERIOD_TILL;
import static ch.openech.model.XmlConstants.SUBSEQUENT_INPUT_TAX_DEDUCTION;
import static ch.openech.model.XmlConstants.SUBSIDIES;
import static ch.openech.model.XmlConstants.SUPPLIES_ABROAD;
import static ch.openech.model.XmlConstants.SUPPLIES_EXEMPT_FROM_TAX;
import static ch.openech.model.XmlConstants.SUPPLIES_PER_TAX_RATE;
import static ch.openech.model.XmlConstants.SUPPLIES_TO_FOREIGN_COUNTRIES;
import static ch.openech.model.XmlConstants.TAX_RATE;
import static ch.openech.model.XmlConstants.TOTAL_CONSIDERATION;
import static ch.openech.model.XmlConstants.TRANSFER_NOTIFICATION_PROCEDURE;
import static ch.openech.model.XmlConstants.TURNOVER;
import static ch.openech.model.XmlConstants.TYPE_OF_SERVICE;
import static ch.openech.model.XmlConstants.TYPE_OF_SUBMISSION;
import static ch.openech.model.XmlConstants.UID_ORGANISATION_ID;
import static ch.openech.model.XmlConstants.UID_ORGANISATION_ID_CATEGORIE;
import static ch.openech.model.XmlConstants._V_A_T_DECLARATION;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.minimalj.util.StringUtils;

import ch.openech.model.emwst.CompilationCompensationExport;
import ch.openech.model.emwst.CompilationDeemedInputTaxDeduction;
import ch.openech.model.emwst.CompilationMarginTaxation;
import ch.openech.model.emwst.EffectiveReportingMethod;
import ch.openech.model.emwst.FlatTaxRateMethod;
import ch.openech.model.emwst.GeneralInformation;
import ch.openech.model.emwst.NetTaxRateMethod;
import ch.openech.model.emwst.OtherFlowsOfFunds;
import ch.openech.model.emwst.SendingApplication;
import ch.openech.model.emwst.TurnoverComputation;
import ch.openech.model.emwst.TurnoverTaxRate;
import ch.openech.model.emwst.VATDeclaration;
import ch.openech.model.emwst.VariousDeduction;
import ch.openech.model.emwst.VerificationCompensationExport;
import ch.openech.model.emwst.VerificationDeemedInputTaxDeduction;
import ch.openech.model.emwst.VerificationMarginTaxation;
import ch.openech.model.organisation.UidStructure;

public class WriterEch0217 extends WriterElement {

	protected final EchSchema context;
	public final String URI;
	
	public WriterEch0217(Writer writer, EchSchema context) {
		super(writer, context.getNamespaceURI(217));
		this.context = context;
		URI = context.getNamespaceURI(217);
	}
	
	public String getURI() {
		return URI;
	}

	public void write(VATDeclaration taxStatement) {
		try {
			startDocument(context, 217, _V_A_T_DECLARATION);
			generalInformation(this, taxStatement.generalInformation);
			turnoverComputation(this, taxStatement.turnoverComputation);
			effectiveReportingMethod(this, taxStatement.effectiveReportingMethod);
			netTaxRateMethod(this, taxStatement.netTaxRateMethod);
			flatTaxRateMethod(this, taxStatement.flatTaxRateMethod);
			values(taxStatement, PAYABLE_TAX);
			otherFlowsOfFunds(this, taxStatement.otherFlowsOfFunds);
			endDocument();
		} catch (Exception x) {
			throw new RuntimeException(x);
		}
	}
	
	private WriterElement create(WriterElement parent, Object object) throws Exception {
		return parent.create(URI, StringUtils.lowerFirstChar(object.getClass().getSimpleName()));
	}
	
	// In der Reihenfolge vom xsd
	
	public void uidStructure(WriterElement parent, UidStructure uid) throws Exception {
		if (uid != null && uid.value != null && uid.value.length() == UidStructure.LENGTH) {
			WriterElement w = parent.create(URI, "uid");
			w.text(UID_ORGANISATION_ID_CATEGORIE, uid.value.substring(0, 3)); // TYPO by schema
			w.text(UID_ORGANISATION_ID, uid.value.substring(3));
		}
    }
	
	private void sendingApplication(WriterElement parent, SendingApplication sendingApplication) throws Exception {
		if (sendingApplication != null) {
			WriterElement w = create(parent, sendingApplication);
			w.values(sendingApplication, MANUFACTURER, PRODUCT, PRODUCT_VERSION);
		}
	}
	
	private void variousDeduction(WriterElement parent, VariousDeduction variousDeduction) throws Exception {
		if (variousDeduction != null) {
			WriterElement w = create(parent, variousDeduction);
			w.values(variousDeduction, AMOUNT_VARIOUS_DEDUCTION, DESCRIPTION_VARIOUS_DEDUCTION);
		}
	}

	private void turnoverTaxRate(WriterElement parent, TurnoverTaxRate turnoverTaxRate, boolean activity) throws Exception {
		turnoverTaxRate(parent, StringUtils.lowerFirstChar(turnoverTaxRate.getClass().getSimpleName()), turnoverTaxRate, activity);
	}
	
	private void turnoverTaxRate(WriterElement parent, String tag, TurnoverTaxRate turnoverTaxRate, boolean activity) throws Exception {
		if (turnoverTaxRate != null) {
			WriterElement w = parent.create(URI, tag);
			if (activity) {
				w.values(turnoverTaxRate, ACTIVITY);
			}
			w.values(turnoverTaxRate, TAX_RATE, TURNOVER);
		}
	}
	
	private void verificationCompensationExport(WriterElement parent, VerificationCompensationExport verificationCompensationExport) throws Exception {
		if (verificationCompensationExport != null) {
			WriterElement w = create(parent, verificationCompensationExport);
			w.values(verificationCompensationExport, INVOICE_NUMBER, INVOICE_DATE, TYPE_OF_SERVICE, TAX_RATE, TURNOVER);
		}
	}
	
	private void compilationCompensationExport(WriterElement parent, CompilationCompensationExport compilationCompensationExport) throws Exception {
		if (compilationCompensationExport != null) {
			WriterElement w = create(parent, compilationCompensationExport);
			for (VerificationCompensationExport e : compilationCompensationExport.verificationCompensationExport) {
				verificationCompensationExport(w, e);
			}
		}
	}
	
	private void verificationDeemedInputTaxDeduction(WriterElement parent, VerificationDeemedInputTaxDeduction verificationDeemedInputTaxDeduction) throws Exception {
		if (verificationDeemedInputTaxDeduction != null) {
			WriterElement w = create(parent, verificationDeemedInputTaxDeduction);
			turnoverTaxRate(w, verificationDeemedInputTaxDeduction.turnoverAndTaxRate, false);
			turnoverTaxRate(w, verificationDeemedInputTaxDeduction.turnoverAndTaxRate, false);
		}
	}
	
	private void compilationDeemedInputTaxDeduction(WriterElement parent, CompilationDeemedInputTaxDeduction compilationDeemedInputTaxDeduction) throws Exception {
		if (compilationDeemedInputTaxDeduction != null) {
			WriterElement w = create(parent, compilationDeemedInputTaxDeduction);
			for (VerificationDeemedInputTaxDeduction e : compilationDeemedInputTaxDeduction.verificationDeemedInputTaxDeduction) {
				verificationDeemedInputTaxDeduction(w, e);
			}
		}
	}

	private void verificationMarginTaxation(WriterElement parent, VerificationMarginTaxation verificationMarginTaxation) throws Exception {
		if (verificationMarginTaxation != null) {
			WriterElement w = create(parent, verificationMarginTaxation);
			turnoverTaxRate(w, verificationMarginTaxation.turnoverAndTaxRate, false);
			turnoverTaxRate(w, verificationMarginTaxation.turnoverAndTaxRate, false);
		}
	}
	
	private void compilationMarginTaxation(WriterElement parent, CompilationMarginTaxation compilationMarginTaxation) throws Exception {
		if (compilationMarginTaxation != null) {
			WriterElement w = create(parent, compilationMarginTaxation);
			for (VerificationMarginTaxation e : compilationMarginTaxation.verificationMarginTaxation) {
				verificationMarginTaxation(w, e);
			}
		}
	}
	
	private void generalInformation(WriterElement parent, GeneralInformation generalInformation) throws Exception {
		if (generalInformation != null) {
			WriterElement w = create(parent, generalInformation);
			uidStructure(w, generalInformation.uid);
			w.values(generalInformation, ORGANISATION_NAME, GENERATION_TIME, REPORTING_PERIOD_FROM, REPORTING_PERIOD_TILL);
			w.values(generalInformation, TYPE_OF_SUBMISSION, FORM_OF_REPORTING, BUSINESS_REFERENCE_ID);
			sendingApplication(w, generalInformation.sendingApplication);
		}
	}

	private void turnoverComputation(WriterElement parent, TurnoverComputation turnoverComputation) throws Exception {
		if (turnoverComputation != null) {
			WriterElement w = create(parent, turnoverComputation);
			w.values(turnoverComputation, TOTAL_CONSIDERATION, SUPPLIES_TO_FOREIGN_COUNTRIES, SUPPLIES_ABROAD, TRANSFER_NOTIFICATION_PROCEDURE);
			w.values(turnoverComputation, SUPPLIES_EXEMPT_FROM_TAX, REDUCTION_OF_CONSIDERATION);
			variousDeduction(w, turnoverComputation.variousDeduction);
		}
	}

	private void effectiveReportingMethod(WriterElement parent, EffectiveReportingMethod effectiveReportingMethod) throws Exception {
		if (effectiveReportingMethod != null) {
			WriterElement w = create(parent, effectiveReportingMethod);
			w.values(effectiveReportingMethod, GROSS_OR_NET, OPTED);
			turnoverTaxRates(w, SUPPLIES_PER_TAX_RATE, effectiveReportingMethod.suppliesPerTaxRate, false);
			turnoverTaxRates(w, ACQUISITION_TAX, effectiveReportingMethod.acquisitionTax, false);
			w.values(effectiveReportingMethod, INPUT_TAX_MATERIAL_AND_SERVICES, INPUT_TAX_INVESTMENTS, SUBSEQUENT_INPUT_TAX_DEDUCTION);
			w.values(effectiveReportingMethod, INPUT_TAX_CORRECTIONS, INPUT_TAX_REDUCTIONS);
		}
	}
	
	private void netTaxRateMethod(WriterElement parent, NetTaxRateMethod netTaxRateMethod) throws Exception {
		if (netTaxRateMethod != null) {
			WriterElement w = create(parent, netTaxRateMethod);
			turnoverTaxRates(w, SUPPLIES_PER_TAX_RATE, netTaxRateMethod.suppliesPerTaxRate, false);
			turnoverTaxRates(w, ACQUISITION_TAX, netTaxRateMethod.acquisitionTax, false);
			
			compilationCompensationExport(w, netTaxRateMethod.compilationCompensationExport);
			w.values(COMPENSATION_EXPORT);
			
			compilationDeemedInputTaxDeduction(w, netTaxRateMethod.compilationDeemedInputTaxDeduction);
			w.values(DEEMED_INPUT_TAX_DEDUCTION);
			
			compilationMarginTaxation(w, netTaxRateMethod.compilationMarginTaxation);
			w.values(MARGIN_TAXATION);
		}
	}
	
	private void flatTaxRateMethod(WriterElement parent, FlatTaxRateMethod flatTaxRateMethod) throws Exception {
		if (flatTaxRateMethod != null) {
			WriterElement w = create(parent, flatTaxRateMethod);
			turnoverTaxRates(w, SUPPLIES_PER_TAX_RATE, flatTaxRateMethod.suppliesPerTaxRate, true); // !!
			turnoverTaxRates(w, ACQUISITION_TAX, flatTaxRateMethod.acquisitionTax, false);
			
			compilationCompensationExport(w, flatTaxRateMethod.compilationCompensationExport);
			w.values(COMPENSATION_EXPORT);
			
			compilationDeemedInputTaxDeduction(w, flatTaxRateMethod.compilationDeemedInputTaxDeduction);
			w.values(DEEMED_INPUT_TAX_DEDUCTION);
			
			compilationMarginTaxation(w, flatTaxRateMethod.compilationMarginTaxation);
			w.values(MARGIN_TAXATION);
		}
	}
	
	private void turnoverTaxRates(WriterElement w, String tag, List<TurnoverTaxRate> turnoverTaxRates, boolean activity) throws Exception {
		if (turnoverTaxRates != null) {
			for (TurnoverTaxRate e : turnoverTaxRates) {
				turnoverTaxRate(w, tag, e, activity);
			}
		}
	}
	
	private void otherFlowsOfFunds(WriterElement parent, OtherFlowsOfFunds otherFlowsOfFunds) throws Exception {
		if (otherFlowsOfFunds != null) {
			WriterElement w = create(parent, otherFlowsOfFunds);
			w.values(otherFlowsOfFunds, SUBSIDIES, DONATIONS);
		}
	}


	public static String writeToString(VATDeclaration taxStatement) {
		StringWriter stringWriter = new StringWriter();
		WriterEch0217 w = new WriterEch0217(stringWriter, EchSchema.getNamespaceContext(217, "1.0"));
		w.write(taxStatement);
		return stringWriter.toString();
	}

}
