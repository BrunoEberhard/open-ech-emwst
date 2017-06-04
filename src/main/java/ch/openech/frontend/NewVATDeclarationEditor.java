package ch.openech.frontend;

import java.util.Objects;

import org.minimalj.frontend.Frontend;
import org.minimalj.frontend.editor.Editor;
import org.minimalj.frontend.form.Form;
import org.minimalj.util.resources.Resources;

import ch.openech.model.emwst.VATDeclaration;

public class NewVATDeclarationEditor extends Editor<VATDeclarationEditModel, VATDeclaration> {

	private final Class<?> reportingMethod;
	
	public NewVATDeclarationEditor(Class<?> reportingMethod) {
		super(getName(reportingMethod));
		Objects.nonNull(reportingMethod);
		this.reportingMethod = reportingMethod;
	}

	private static String getName(Class<?> reportingMethod) {
		return Resources.getString("NewVATDeclarationEditor." + reportingMethod.getSimpleName());
	}

	@Override
	protected VATDeclarationEditModel createObject() {
		VATDeclarationEditModel declaration = new VATDeclarationEditModel(reportingMethod);

		declaration.setDefaultRates();
		return declaration;
	}
	
	@Override
	protected Form<VATDeclarationEditModel> createForm() {
		return new VATDeclarationForm(true, reportingMethod);
	}

	@Override
	protected VATDeclaration save(VATDeclarationEditModel declaration) {
		return declaration.convertToModel();
	}
	
	@Override
	protected void finished(VATDeclaration result) {
		Frontend.show(new VATDeclarationPage(result));
	}
}
