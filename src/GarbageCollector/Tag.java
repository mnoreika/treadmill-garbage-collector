package GarbageCollector;

import java.util.ArrayList;

public class Tag extends Cell {
    private DataType type;
    private ArrayList<Cell> entries;

    public Tag(DataType type) {
        super();

        this.type = type;
        entries = new ArrayList<Cell>();
    }

    public void addEntry(Cell cell) {
        entries.add(cell);
    }

    public ArrayList<Cell> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "" + type;
    }
}
