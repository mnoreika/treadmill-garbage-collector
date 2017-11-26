package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.Data;

import java.util.ArrayList;

public class Integer extends Data {
    private int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public ArrayList<Cell> allocate() {
        return null;
    }
}
