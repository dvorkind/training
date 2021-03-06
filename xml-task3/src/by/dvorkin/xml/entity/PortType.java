package by.dvorkin.xml.entity;

public enum PortType {
    HDMI("HDMI (High Definition Multimedia Interface)"),
    VGA("VGA (Video Graphics Array)"),
    USB("USB (Universal Serial Bus)"),
    SOCKET("CPU socket"),
    PCI_E("PCI-E (Peripheral component interconnect Express)"),
    SATA("Serial ATA"),
    IDE("IDE (Integrated Drive Electronics)");

    private String portName;

    PortType(String portName) {
        this.portName = portName;
    }

    public String getPortName() {
        return portName;
    }
}
