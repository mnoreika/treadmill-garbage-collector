package GarbageCollector;

import GarbageCollector.Cell;
import GarbageCollector.DataType;

import java.util.ArrayList;

public abstract class Data extends Cell {
    private DataType type;

    public Data() {
        super();
    }

    public abstract ArrayList<Cell> allocate();
}
