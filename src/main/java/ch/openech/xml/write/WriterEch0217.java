package ch.openech.xml.write;

import static ch.openech.model.XmlConstants.UID_ORGANISATION_ID;
import static ch.openech.model.XmlConstants.UID_ORGANISATION_ID_CATEGORIE;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.minimalj.model.properties.Properties;
import org.minimalj.model.properties.PropertyInterface;
import org.minimalj.util.FieldUtils;

import ch.openech.model.emwst.VATDeclaration;
import ch.openech.model.organisation.UidStructure;
import ch.openech.model.types.EchCode;

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
			startDocument(context, 217, "VATDeclaration");
			write(this, taxStatement, URI);
			endDocument();
		} catch (Exception x) {
			throw new RuntimeException(x);
		}
	}

	private void write(WriterElement child, Object object, String URI) throws Exception {
		List<PropertyInterface> elementProperties = new ArrayList<>();
		for (PropertyInterface property : Properties.getProperties(object.getClass()).values()) {
			String name = property.getName();
			if (FieldUtils.isAllowedPrimitive(property.getClazz())) {
				Object value = property.getValue(object);
				if (value != null) {
					child.text(name, value.toString());
				}
			} else if (EchCode.class.isAssignableFrom(property.getClazz())) {
				EchCode code = (EchCode) property.getValue(object);
				if (code != null) {
					child.text(name, code.getValue());
				}
			} else {
				elementProperties.add(property);
			}
		}
		for (PropertyInterface property : elementProperties) {
			String name = property.getName();
			if (name.equals("id")) {
				continue;
			}
			Object value = property.getValue(object);

			if (value instanceof UidStructure) {
				uidStructure(child, "uid", (UidStructure) value);
			} else if (value instanceof List) {
				List<?> list = (List<?>) value;
				for (Object item : list) {
					WriterElement child2 = child.create(URI, name);
					write(child2, item, URI);
				}
			} else if (value != null && value.getClass().getName().startsWith("ch.openech")) {
				WriterElement child2 = child.create(URI, name);
				write(child2, value, URI);
			}
		}
	}
	
	public void uidStructure(WriterElement parent, String tagName, UidStructure uid) throws Exception {
		if (uid != null && uid.value != null && uid.value.length() == UidStructure.LENGTH) {
			WriterElement uidStructure = parent.create(URI, tagName);
			uidStructure.text(UID_ORGANISATION_ID_CATEGORIE, uid.value.substring(0, 3)); // TYPO by schema
			uidStructure.text(UID_ORGANISATION_ID, uid.value.substring(3));
		}
    }

	public static String writeToString(VATDeclaration taxStatement) {
		StringWriter stringWriter = new StringWriter();
		WriterEch0217 w = new WriterEch0217(stringWriter, EchSchema.getNamespaceContext(196, "1.0"));
		w.write(taxStatement);
		return stringWriter.toString();
	}

}
