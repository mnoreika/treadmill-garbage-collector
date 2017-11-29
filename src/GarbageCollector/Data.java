package GarbageCollector;

public class Data extends Cell {
    private Object data;

    public Data(Object data) {
        super();

        this.data = data;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "<" + data + ">";
    }
}
