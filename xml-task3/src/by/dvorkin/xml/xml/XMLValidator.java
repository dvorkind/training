package by.dvorkin.xml.xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class XMLValidator extends DefaultHandler {
    private String pathXml;
    private String pathXsd;

    public XMLValidator(String pathXml, String pathXsd) {
        this.pathXml = pathXml;
        this.pathXsd = pathXsd;
    }

    public void validate() {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));
        } catch (SAXParseException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
