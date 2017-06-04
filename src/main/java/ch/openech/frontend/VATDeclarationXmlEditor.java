package ch.openech.frontend;

import java.util.Collections;
import java.util.List;

import org.minimalj.backend.Backend;
import org.minimalj.frontend.action.Action;
import org.minimalj.frontend.editor.Editor;
import org.minimalj.frontend.form.Form;
import org.minimalj.frontend.form.element.StringFormElement;
import org.minimalj.model.Keys;
import org.minimalj.model.annotation.NotEmpty;
import org.minimalj.model.annotation.Size;
import org.minimalj.model.validation.ValidationMessage;
import org.minimalj.util.StringUtils;

import ch.openech.frontend.VATDeclarationXmlEditor.XmlValue;
import ch.openech.model.EchSchemaValidation;
import ch.openech.model.emwst.VATDeclaration;
import ch.openech.xml.read.StaxEch0217;
import ch.openech.xml.write.WriterEch0217;

public class VATDeclarationXmlEditor extends Editor<XmlValue, VATDeclaration> {
	private final VATDeclarationPage vatDeclarationPage;
	
	public VATDeclarationXmlEditor(VATDeclarationPage vatDeclarationPage) {
		this.vatDeclarationPage = vatDeclarationPage;
	}
	
	@Override
	protected Form<XmlValue> createForm() {
		Form<XmlValue> form = new Form<XmlValue>(true) {
			@Override
			protected int getColumnWidthPercentage() {
				return 400;
			}
		};
		
		form.line(XmlValue.$.requireValid);
		form.line(new StringFormElement(Keys.getProperty(XmlValue.$.xml), true));
		
		if ("true".equals(System.getProperty("onHeroku"))) {
			form.text("Hinweis: Bitte verwenden sie keine echten Daten, da diese Applikation auf öffentlichen Servern betrieben wird");
		}
		return form;
	}
	
	@Override
	protected List<Action> createAdditionalActions() {
		// No Demo - Action, even in Dev - Mode
		return Collections.emptyList();
	}
	
	public static class XmlValue {
		public static final XmlValue $ = Keys.of(XmlValue.class);
		
		public Boolean requireValid = true;
		
		@Size(1024*1024) @NotEmpty
		public String xml;
	}


	@Override
	protected void validate(XmlValue value, List<ValidationMessage> validationMessages) {
		if (value.requireValid && !StringUtils.isBlank(value.xml)) {
			try {
				String result = EchSchemaValidation.validate(value.xml);
				if (!EchSchemaValidation.OK.equals(result)) {
					validationMessages.add(new ValidationMessage(XmlValue.$.xml, result));
				}
			} catch (Exception x) {
				// todo move this to EchSchemaValidation
				validationMessages.add(new ValidationMessage(XmlValue.$.xml, "Fehler bei Validierung"));
			}
		}
	}
	
	@Override
	protected XmlValue createObject() {
		XmlValue xmlValue = new XmlValue();
		String s;
		try {
			s = WriterEch0217.writeToString(vatDeclarationPage.getObject());
		} catch (Exception x) {
			x.printStackTrace();
			s = "Writer failed: " + x.getLocalizedMessage();
		}
		xmlValue.xml = s;
		boolean validAtStart = EchSchemaValidation.OK.equals(EchSchemaValidation.validate(s));
		xmlValue.requireValid = validAtStart;
		return xmlValue;
	}
	
	@Override
	protected VATDeclaration save(XmlValue xmlValue) {
		VATDeclaration vatDeclaration = new StaxEch0217().process(xmlValue.xml);
		return Backend.save(vatDeclaration);
	}
	
	@Override
	protected void finished(VATDeclaration result) {
		vatDeclarationPage.setObject(result);
	}
}
