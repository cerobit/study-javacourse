package co.com.bancolombia.model.event;

public enum BoxEventType {
    CREATE("box.create"),
    DELETE("box.delete"),
    UPDATE("box.update"),
    UPDATE_NAME("box.updatename"),
    BOX("box.box");

    private final String value;

    BoxEventType(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}

