package ch.openech.frontend;

import java.util.ArrayList;
import java.util.List;

import org.minimalj.frontend.Frontend.IContent;
import org.minimalj.frontend.action.Action;
import org.minimalj.frontend.form.Form;
import org.minimalj.frontend.page.Page;

import ch.openech.model.emwst.VATDeclaration;

public class VATDeclarationPage extends Page {

	private VATDeclaration declaration;
	private VATDeclarationForm form;
	
	public VATDeclarationPage(VATDeclaration declaration) {
 		this.declaration = declaration;
 	}

	public IContent getContent() {
		VATDeclarationForm form = getForm();
		form.setObject(new VATDeclarationEditModel(declaration));
		return form.getContent();
	}
	
	private VATDeclarationForm getForm() {
		if (form == null) {
			form = new VATDeclarationForm(Form.READ_ONLY, declaration.getClass());
		}
		return form;
	}
	
	public void setObject(VATDeclaration declaration) {
 		this.declaration = declaration;
 		getForm().setObject(new VATDeclarationEditModel(declaration));
 	}
	
	public VATDeclaration getObject() {
		return declaration;
	}
	
	@Override
	public List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		actions.add(new VATDeclarationEditor(this));
		actions.add(new VATDeclarationXmlEditor(this));
		return actions;
	}
	
}
