package by.dvorkin.xml.xml;

import by.dvorkin.xml.entity.Component;
import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StAXWriter {
    public void writeSingleElement(XMLStreamWriter writer, String elementName, String elementValue) throws XMLStreamException {
        writer.writeStartElement(elementName);
        writer.writeCharacters(elementValue);
        writer.writeEndElement();
    }

    public void write(List<Component> components, String fileName) {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer;
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            writer = new IndentingXMLStreamWriter(factory.createXMLStreamWriter(fileWriter));
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("components");
            writer.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("xmlns", "http://www.dvorkin.by/components");
            writer.writeAttribute("xsi:schemaLocation", "http://www.dvorkin.by/components components.xsd");
            for (Component component : components) {
                writer.writeStartElement("component");
                writer.writeAttribute("id", component.getId());
                writeSingleElement(writer, "name", component.getName());
                writeSingleElement(writer, "origin", component.getOrigin());
                writeSingleElement(writer, "price", component.getPrice().toString());
                writer.writeStartElement("parameters");
                writeSingleElement(writer, "peripheral", component.isPeripheral().toString());
                if (component.getPowerUsage() != null) {
                    writeSingleElement(writer, "powerUsage", component.getPowerUsage().toString());
                }
                writeSingleElement(writer, "hasCooler", component.isHasCooler().toString());
                writeSingleElement(writer, "group", component.getGroupType());
                if (component.getPortType() != null) {
                    writeSingleElement(writer, "port", component.getPortType());
                }
                writer.writeEndElement();
                writeSingleElement(writer, "critical", component.isCritical().toString());
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
