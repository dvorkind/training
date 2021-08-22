package by.dvorkin.web.model.entity;

public enum Role {
    ADMINISTRATOR("role.admin"),
    SUBSCRIBER("role.user");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }
}
