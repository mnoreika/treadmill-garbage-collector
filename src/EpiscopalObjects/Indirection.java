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
        Data data = new Data(cell);

        tag.addEntry(data);

        blocks.add(tag);
        blocks.add(data);

        return blocks;
    }

    @Override
    public int getSize() {
        return MIN_SIZE;
    }
}
