package ch.openech.frontend;

import org.minimalj.frontend.Frontend.IContent;
import org.minimalj.frontend.form.Form;
import org.minimalj.frontend.page.Page;

import ch.openech.model.emwst.VATDeclaration;

public class VATDeclarationPage extends Page {

	private final VATDeclaration declaration;

	public VATDeclarationPage(VATDeclaration declaration) {
 		this.declaration = declaration;
 	}

	public IContent getContent() {
		VATDeclarationForm form = new VATDeclarationForm(Form.READ_ONLY, declaration.getClass());
		form.setObject(new VATDeclarationEditModel(declaration));
		return form.getContent();
	}
}
