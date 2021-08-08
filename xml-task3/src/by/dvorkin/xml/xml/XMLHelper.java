package by.dvorkin.xml.xml;

import by.dvorkin.xml.entity.Component;
import by.dvorkin.xml.entity.GroupType;

import java.util.List;
import java.util.stream.Collectors;

public class XMLHelper {
    private final String SOURCEXML = "xml-task3/resources/input/components.xml";
    private List<Component> components;
    private String error;

    public String getError() {
        return error;
    }

    public String getSourceXML() {
        return SOURCEXML;
    }

    public int getComponentSize() {
        return components.size();
    }

    public boolean validate(String fileName) {
        String SOURCEXSD = "xml-task3/resources/input/components.xsd";
        XMLValidator validator = new XMLValidator(fileName, SOURCEXSD);
        if (!validator.validate()) {
            error = validator.getErrorMsg();
            return false;
        }
        return true;
    }

    public void xmlToObjects() {
        StAXReader xmlReader = new StAXReader();
        components = xmlReader.parse(SOURCEXML);
    }

    public boolean addPercentToPrice(int percent) {
        if (percent < -100) {
            error = "Percent cannot be less than -100!";
            return false;
        }
        for (Component component : components) {
            component.setPrice((double) Math.round((component.getPrice() + (component.getPrice() / 100 * percent)) * 100) / 100);
        }
        return true;
    }

    public void sortComponentsByPrice(Boolean up) {
        components.sort((component1, component2) -> {
            if (up) {
                return component1.getPrice().compareTo(component2.getPrice());
            }
            return component2.getPrice().compareTo(component1.getPrice());
        });
    }

    public boolean createAllSeparatedXML() {
        for (GroupType groupType : GroupType.values()) {
            if (!createSeparateXml(components, groupType)) {
                return false;
            }
        }
        return true;
    }

    public boolean createSeparateXml(List<Component> components, GroupType groupType) {
        String groupName = groupType.toString().toLowerCase();
        String fileName = "xml-task3/resources/output/" + groupName + ".xml";

        List<Component> collect = components.stream().filter((p) -> p.getGroupType().toLowerCase().equals(groupName))
                .collect(Collectors.toList());
        StAXWriter xmlWriter = new StAXWriter();
        xmlWriter.write(collect, fileName);
        return validate(fileName);
    }

    public StringBuilder showComponents() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Component component : components) {
            stringBuilder.append(component).append('\n');
        }
        return stringBuilder;
    }
}
