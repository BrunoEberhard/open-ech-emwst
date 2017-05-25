package ch.openech.frontend;

import java.text.MessageFormat;
import java.util.Objects;

import org.minimalj.backend.Backend;
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
		String resourceName = Resources.getString(NewObjectEditor.class);
		String classText = Resources.getResourceName(reportingMethod);
		return MessageFormat.format(resourceName, classText);
	}

	@Override
	protected VATDeclarationEditModel createObject() {
		VATDeclarationEditModel declaration = new VATDeclarationEditModel(reportingMethod);
		return declaration;
	}
	
	@Override
	protected Form<VATDeclarationEditModel> createForm() {
		return new VATDeclarationForm(true, reportingMethod);
	}

	@Override
	protected VATDeclaration save(VATDeclarationEditModel declaration) {
		return Backend.save(declaration.convertToModel());
	}
	
	@Override
	protected void finished(VATDeclaration result) {
		Frontend.show(new VATDeclarationPage(result));
	}
}
