package ch.openech.emwst;

import java.io.InputStream;
import java.io.StringWriter;

import org.junit.Test;

import ch.openech.model.EchSchemaValidation;
import ch.openech.model.emwst.VATDeclaration;
import ch.openech.xml.read.StaxEch0217;
import ch.openech.xml.write.EchSchema;
import ch.openech.xml.write.WriterEch0217;

public class EmwstXmlTest {

	@Test
	public void test() {
		InputStream stream = this.getClass().getResourceAsStream("sample.xml");
		StaxEch0217 reader = new StaxEch0217();
		VATDeclaration declaration = reader.process(stream);
		
		String output = writeToString(declaration);
		System.out.println(output);
		System.out.println(EchSchemaValidation.validate(output));
	}

	
	public static String writeToString(VATDeclaration declaration) {
		StringWriter stringWriter = new StringWriter();
		WriterEch0217 w = new WriterEch0217(stringWriter, EchSchema.getNamespaceContext(217, "1.0"));
		w.write(declaration);
		return stringWriter.toString();
	}

}
