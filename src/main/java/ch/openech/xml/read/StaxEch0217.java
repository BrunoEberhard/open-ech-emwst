package ch.openech.xml.read;

import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.minimalj.model.EnumUtils;
import org.minimalj.model.properties.Properties;
import org.minimalj.model.properties.PropertyInterface;
import org.minimalj.util.CloneHelper;
import org.minimalj.util.GenericUtils;

import ch.openech.model.emwst.VATDeclaration;
import ch.openech.model.organisation.UidStructure;

public class StaxEch0217 {

	public static final String PKG = VATDeclaration.class.getPackage().getName();
	
	public VATDeclaration process(String xmlString) {
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader xml = inputFactory.createXMLEventReader(new StringReader(xmlString));
			
			VATDeclaration ts = process(xml, VATDeclaration.class);
			xml.close();
			return ts;
		} catch (XMLStreamException x) {
			throw new RuntimeException(x);
		}
	}
	
	public VATDeclaration process(InputStream stream) {
		try {
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader xml = inputFactory.createXMLEventReader(stream);
			
			VATDeclaration ts = process(xml, VATDeclaration.class);
			xml.close();
			return ts;
		} catch (XMLStreamException x) {
			throw new RuntimeException(x);
		}
	}

	private <T> T process(XMLEventReader xml, Class<T> clazz) throws XMLStreamException {
		T object = null;
		while (xml.hasNext()) {
			XMLEvent event = xml.nextEvent();
			if (event.isStartElement()) {
				if (clazz == UidStructure.class) {
					UidStructure uid = new UidStructure();
					StaxEch0097.uidStructure(xml, uid);
					return (T) uid;
				}
				if (clazz == LocalDateTime.class) {
					return (T)StaxEch.dateTime(xml);
				} else if (clazz == LocalDate.class) {
					return (T)StaxEch.date(xml);
				} else if (Enum.class.isAssignableFrom(clazz)) {
					int value = StaxEch.integer(xml);
					return (T) EnumUtils.valueList((Class<Enum>) clazz).get(value - 1);
				} else if (clazz == BigDecimal.class) {
					return (T) new BigDecimal(StaxEch.token(xml));
				} else if (clazz == String.class) {
					return (T) StaxEch.token(xml);
				}
				object = CloneHelper.newInstance(clazz);
				break;
			}
		}
		
		while (xml.hasNext()) {
			XMLEvent event = xml.peek();
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				String startName = startElement.getName().getLocalPart();
				PropertyInterface property = Properties.getProperty(object.getClass(), startName);
				Class<?> elementClass = property.getClazz();
				if (elementClass == List.class) {
					Object list = property.getValue(object);
					if (list == null) {
						list = new ArrayList<>();
						property.setValue(object, list);
					}
					Object value = process(xml, GenericUtils.getGenericClass(property.getType()));
					((List) list).add(value);
				} else {
					Object value = process(xml, elementClass);
					if (!property.isFinal()) {
						property.setValue(object, value);
					} else if (value != null) {
						CloneHelper.deepCopy(value, property.getValue(object));
					}
				}
			} else {
				event = xml.nextEvent();
				if (event.isEndElement()) {
					break;
				}
			}
		}
		return object;
	}

}
