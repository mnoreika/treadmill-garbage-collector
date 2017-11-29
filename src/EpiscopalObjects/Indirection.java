package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.Data;
import GarbageCollector.DataType;
import GarbageCollector.Tag;

import java.util.ArrayList;

public class Indirection extends  EpiscopalObject {
    private Cell cell;

    public Indirection(Cell cell) {
        this.cell = cell;
    }

    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks = new ArrayList<Cell>();

        Tag tag = new Tag(DataType.IND);
        Data data = (Data) ((Tag) cell).getEntries().get(0);

        tag.addEntry((data));

        blocks.add(tag);

        return blocks;
    }

    @Override
    public int getSize() {
        return MIN_SIZE;
    }

    @Override
    public String toString() {
        return "Ind";
    }
}
