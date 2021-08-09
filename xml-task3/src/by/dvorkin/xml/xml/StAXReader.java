package by.dvorkin.xml.xml;

import by.dvorkin.xml.entity.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StAXReader {
    public List<Component> parse(String fileName) {
        XMLStreamReader reader = null;
        try {
            List<Component> components = new ArrayList<>();
            Component component = new Component();
            reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT: {
                        String tagName = reader.getLocalName();
                        switch (tagName) {
                            case "component":
                                component = new Component();
                                component.setId(reader.getAttributeValue(0));
                                break;
                            case "name":
                                component.setName(reader.getElementText());
                                break;
                            case "origin":
                                component.setOrigin(reader.getElementText());
                                break;
                            case "price":
                                component.setPrice(Double.parseDouble(reader.getElementText()));
                                break;
                            case "peripheral":
                                component.setPeripheral(Boolean.parseBoolean(reader.getElementText()));
                                break;
                            case "powerUsage":
                                component.setPowerUsage(Double.parseDouble(reader.getElementText()));
                                break;
                            case "hasCooler":
                                component.setHasCooler(Boolean.parseBoolean(reader.getElementText()));
                                break;
                            case "group":
                                component.setGroupType(reader.getElementText());
                                break;
                            case "port":
                                component.setPortType(reader.getElementText());
                                break;
                            case "critical":
                                component.setCritical(Boolean.parseBoolean(reader.getElementText()));
                                break;
                            default:
                                break;
                        }
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT: {
                        String tagName = reader.getLocalName();
                        if (tagName.equals("component")) {
                            components.add(component);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
            return components;
        } catch (XMLStreamException | FileNotFoundException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException ignored) {
                }
            }
        }
    }
}
