package ch.openech.frontend;

import org.minimalj.frontend.Frontend;
import org.minimalj.frontend.editor.Editor;
import org.minimalj.frontend.form.Form;

import ch.openech.model.emwst.VATDeclaration;

public class VATDeclarationEditor extends Editor<VATDeclarationEditModel, VATDeclaration> {

	private final VATDeclarationPage page;
	
	public VATDeclarationEditor(VATDeclarationPage page) {
		super();
		this.page = page;
	}

	@Override
	protected VATDeclarationEditModel createObject() {
		return new VATDeclarationEditModel(page.getObject());
	}
	
	@Override
	protected Form<VATDeclarationEditModel> createForm() {
		return new VATDeclarationForm(true, page.getObject().getReportingMethod().getClass());
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
