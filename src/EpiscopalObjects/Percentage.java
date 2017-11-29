package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.Data;
import GarbageCollector.DataType;
import GarbageCollector.Tag;

import java.util.ArrayList;

public class Percentage extends EpiscopalObject {
    private float value;

    public Percentage(float value) {
        this.value = value;
    }

    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks = new ArrayList<>();

        Tag tag = new Tag(DataType.PERCENTAGE);
        Data data = new Data(value);

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