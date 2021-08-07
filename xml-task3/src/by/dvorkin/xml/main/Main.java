package by.dvorkin.xml.main;

import by.dvorkin.xml.entity.Component;
import by.dvorkin.xml.xmlHandler.XmlReader;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Source xmlFile = new StreamSource(new File("xml-task3/resources/components.xml"));
        File schemaFile = new File("xml-task3/resources/components.xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
        } catch (IOException ignored) {
        }
        XmlReader xmlReader = new XmlReader();
        List<Component> components = xmlReader.parse("xml-task3/resources/components.xml");

        for (Component component : components) {
            System.out.println(component);
        }
    }
}
