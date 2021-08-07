package by.dvorkin.xml.entity;

public class Component {
    private String id;
    private String name;
    private String origin;
    private double price;
    private boolean peripheral;
    private double powerUsage;
    private boolean hasCooler;
    private String groupType;
    private String portType;
    private boolean critical;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPeripheral() {
        return peripheral;
    }

    public void setPeripheral(boolean peripheral) {
        this.peripheral = peripheral;
    }

    public double getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(double powerUsage) {
        this.powerUsage = powerUsage;
    }

    public boolean isHasCooler() {
        return hasCooler;
    }

    public void setHasCooler(boolean hasCooler) {
        this.hasCooler = hasCooler;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\t').append(id).append(". ").append(name).append('\n')
                .append("Country of origin: ").append('\t').append(origin).append('\n')
                .append("Price: ").append("\t\t\t\t").append(price).append('\n')
                .append("Peripheral device: ").append('\t').append(peripheral).append('\n');
        if (powerUsage != 0) {
            stringBuilder.append("Power usage: ").append("\t\t").append(powerUsage).append('\n');
        }
        stringBuilder.append("Has cooler: ").append("\t\t").append(hasCooler).append('\n')
                .append("Belongs to group: ").append('\t').append(GroupType.valueOf(groupType).getGroupName()).append('\n');
        if (portType != null) {
            stringBuilder.append("Connects by port: ").append('\t').append(portType.replace('_', '-')).append('\n');
        }
        stringBuilder.append("Is critical: ").append("\t\t").append(critical).append('\n');
        return stringBuilder.toString();
    }
}