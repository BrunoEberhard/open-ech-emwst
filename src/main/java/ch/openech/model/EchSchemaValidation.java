package ch.openech.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import ch.openech.xml.read.LSInputImpl;

public class EchSchemaValidation {
	public static final Logger logger = Logger.getLogger(EchSchemaValidation.class.getName());
	public static final String OK = "ok";

	//

	private static Validator validator;

	static Validator getValidator() throws SAXException {
		if (validator == null) {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			LSResourceResolver resourceResolver = new LSResourceResolver() {
				@Override
				public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
						String baseURI) {
					if (systemId == null)
						return null;

					int pos = systemId.lastIndexOf("/");
					String fileName = "/ch/ech/xmlns" + systemId.substring(pos);
					InputStream stream = this.getClass().getResourceAsStream(fileName);

					return new LSInputImpl(publicId, systemId, baseURI, stream, null);
				}
			};

			schemaFactory.setResourceResolver(resourceResolver);
			Schema schema = schemaFactory.newSchema();
			validator = schema.newValidator();
			validator.setResourceResolver(resourceResolver);
		}
		return validator;
	}

	public static String validate(String string) {
		String message = OK;
		try {
			getValidator().validate(new StreamSource(new StringReader(string)));
		} catch (SAXParseException parseException) {
			message = "XML invalid:\n";
			message += parseException.getLocalizedMessage() + "\n";
			message += "Line: " + parseException.getLineNumber();
			message += " - Column: " + parseException.getColumnNumber();
		} catch (SAXException e) {
			message = e.getLocalizedMessage();
			e.printStackTrace();
		} catch (IOException e) {
			message = e.getLocalizedMessage();
			e.printStackTrace();
		}
		return message;
	}

}
