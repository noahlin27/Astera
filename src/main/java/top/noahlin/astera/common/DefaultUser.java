package top.noahlin.astera.common;

public enum DefaultUser {
    ANONYMOUS_USER(1, "anonymousUser"),
    SYSTEM_USER(2, "systemUser");

    private int id;
    private String name;

    DefaultUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
