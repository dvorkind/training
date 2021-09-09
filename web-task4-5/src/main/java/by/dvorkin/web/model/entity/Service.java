package by.dvorkin.web.model.entity;

public class Service extends Entity {
    private String name;
    private String description;
    private int price;

    public Service() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (getPrice() != service.getPrice()) return false;
        if (getId() != null ? !getId().equals(service.getId()) : service.getId() != null) return false;
        if (getName() != null ? !getName().equals(service.getName()) : service.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(service.getDescription()) :
                service.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getPrice();
        return result;
    }
}
