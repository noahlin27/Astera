package top.noahlin.astera.common;

public enum EntityType {
    USER(1, "user"),
    QUESTION(2, "question"),
    COMMENT(3, "comment");

    private int typeId;
    private String typeName;

    EntityType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
