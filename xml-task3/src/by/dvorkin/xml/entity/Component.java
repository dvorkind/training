package by.dvorkin.xml.entity;

public class Component {
    private String id;
    private String name;
    private String origin;
    private Double price;
    private Boolean peripheral;
    private Double powerUsage;
    private Boolean hasCooler;
    private String groupType;
    private String portType;
    private Boolean critical;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isPeripheral() {
        return peripheral;
    }

    public void setPeripheral(Boolean peripheral) {
        this.peripheral = peripheral;
    }

    public Double getPowerUsage() {
        return powerUsage;
    }

    public void setPowerUsage(Double powerUsage) {
        this.powerUsage = powerUsage;
    }

    public Boolean isHasCooler() {
        return hasCooler;
    }

    public void setHasCooler(Boolean hasCooler) {
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

    public Boolean isCritical() {
        return critical;
    }

    public void setCritical(Boolean critical) {
        this.critical = critical;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('\t').append(id).append(". ").append(name).append('\n').append("Country of origin: ")
                .append('\t').append(origin).append('\n').append("Price: ").append("\t\t\t\t").append(price)
                .append('\n').append("Peripheral device: ").append('\t').append(peripheral).append('\n');
        if (powerUsage != null) {
            stringBuilder.append("Power usage: ").append("\t\t").append(powerUsage).append('\n');
        }
        stringBuilder.append("Has cooler: ").append("\t\t").append(hasCooler).append('\n').append("Belongs to group: ")
                .append('\t').append(GroupType.valueOf(groupType).getGroupName()).append('\n');
        if (portType != null) {
            stringBuilder.append("Connects by port: ").append('\t').append(PortType.valueOf(portType).getPortName())
                    .append('\n');
        }
        stringBuilder.append("Is critical: ").append("\t\t").append(critical).append('\n');
        return stringBuilder.toString();
    }
}