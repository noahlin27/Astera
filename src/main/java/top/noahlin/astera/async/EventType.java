package top.noahlin.astera.async;

public enum EventType {
    LOGIN(0),
    COMMENT(1),
    LIKE(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5);

    private final int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
