package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.Data;
import GarbageCollector.DataType;
import GarbageCollector.Tag;

import java.util.ArrayList;

public class Bool extends EpiscopalObject {
    private boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks = new ArrayList<>();

        Tag tag = new Tag(DataType.BOOL);
        Data data = new Data(value);

        tag.addEntry(data);

        blocks.add(tag);
        blocks.add(data);

        return blocks;
    }
}