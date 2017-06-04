package ch.openech.frontend;

import org.minimalj.frontend.Frontend;

import ch.openech.model.emwst.VATDeclaration;

public class VATDeclarationXmlImport extends VATDeclarationXmlEditor {

	public VATDeclarationXmlImport() {
		super(null);
	}

	@Override
	protected XmlValue createObject() {
		XmlValue xmlValue = new XmlValue();
		xmlValue.xml = "";
		return xmlValue;
	}

	@Override
	protected void finished(VATDeclaration result) {
		Frontend.show(new VATDeclarationPage(result));
	}
}
