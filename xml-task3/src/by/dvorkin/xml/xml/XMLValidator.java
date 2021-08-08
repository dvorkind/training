package by.dvorkin.xml.xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLValidator extends DefaultHandler {
    private String pathXml;
    private String pathXsd;
    private String errorMsg;

    public XMLValidator(String pathXml, String pathXsd) {
        this.pathXml = pathXml;
        this.pathXsd = pathXsd;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMessage) {
        this.errorMsg = errorMessage;
    }

    @Override
    public void error(SAXParseException e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error in line ").append(e.getLineNumber()).append(", column ").append(e.getColumnNumber())
                .append(" of xml source file\n").append(e.getMessage()).append('\n');
        setErrorMsg(stringBuilder.toString());
    }

    public boolean validate() {
        File xml = new File(pathXml);
        File xsd = new File(pathXsd);
        if (!xml.exists()) {
            setErrorMsg("XML not found: " + pathXml);
            return false;
        }
        if (!xsd.exists()) {
            setErrorMsg("XSD not found: " + pathXsd);
            return false;
        }
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(this);
            validator.validate(new StreamSource(pathXml));
            return getErrorMsg() == null;
        } catch (SAXException | IOException e) {
            return false;
        }
    }
}
