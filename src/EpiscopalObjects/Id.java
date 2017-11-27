package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.DataType;
import GarbageCollector.Tag;
import GarbageCollector.Data;

import java.util.ArrayList;

public class Id extends EpiscopalObject {
    private int variableIndex;

    public Id(int variableIndex) {
        this.variableIndex = variableIndex;
    }


    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks = new ArrayList<>();

        Tag tag = new Tag(DataType.ID);
        Data data = new Data(variableIndex);

        tag.addEntry(data);

        blocks.add(tag);
        blocks.add(data);

        return blocks;
    }
}
