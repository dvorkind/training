package by.dvorkin.xml.xml;

import by.dvorkin.xml.entity.Component;
import by.dvorkin.xml.entity.GroupType;

import java.util.List;
import java.util.stream.Collectors;

public class XMLHelper {
    private final String SOURCEXML = "xml-task3/resources/input/components.xml";
    private List<Component> components;

    public String getSourceXML() {
        return SOURCEXML;
    }

    public int getComponentSize() {
        return components.size();
    }

    public void validate(String fileName) {
        String SOURCEXSD = "xml-task3/resources/input/components.xsd";
        XMLValidator validator = new XMLValidator(fileName, SOURCEXSD);
        validator.validate();
    }

    public void xmlToObjects() {
        StAXReader xmlReader = new StAXReader();
        components = xmlReader.parse(SOURCEXML);
    }

    public void addPercentToPrice(int percent) {
        if (percent < -100) {
            System.out.println("Percent cannot be less than -100!");
            System.exit(-1);
        }
        for (Component component : components) {
            component.setPrice((double) Math.round((component.getPrice() + (component.getPrice() / 100 * percent)) * 100) / 100);
        }
    }

    public void sortComponentsByPrice(Boolean up) {
        components.sort((component1, component2) -> {
            if (up) {
                return component1.getPrice().compareTo(component2.getPrice());
            }
            return component2.getPrice().compareTo(component1.getPrice());
        });
    }

    public void createAllSeparatedXML() {
        for (GroupType groupType : GroupType.values()) {
            createSeparateXml(components, groupType);
        }
    }

    public void createSeparateXml(List<Component> components, GroupType groupType) {
        String groupName = groupType.toString().toLowerCase();
        String fileName = "xml-task3/resources/output/" + groupName + ".xml";

        List<Component> collect = components.stream().filter((p) -> p.getGroupType().toLowerCase().equals(groupName))
                .collect(Collectors.toList());
        StAXWriter xmlWriter = new StAXWriter();
        xmlWriter.write(collect, fileName);
        validate(fileName);
    }

    public StringBuilder showComponents() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Component component : components) {
            stringBuilder.append(component).append('\n');
        }
        return stringBuilder;
    }
}
