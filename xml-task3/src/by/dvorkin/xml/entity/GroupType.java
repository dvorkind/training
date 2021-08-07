package by.dvorkin.xml.entity;

public enum GroupType {
    MONITOR("monitor"),
    KEYBOARD("keyboard"),
    MOUSE("mouse"),
    MOTHERBOARD("motherboard"),
    PROCESSOR("processor"),
    MEMORY("memory"),
    VIDEOCARD("graphics card"),
    STORAGE("storage"),
    POWERSUPPLY("power supply");

    private String groupName;

    public String getGroupName(){
        return groupName;
    }

    GroupType(String groupName) {
        this.groupName = groupName;
    }
}
