package top.noahlin.astera.common;

public enum EntityType {
    USER(1),
    QUESTION(2),
    COMMENT(3);

    private final int value;

    EntityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
