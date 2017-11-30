package EpiscopalObjects;

import GarbageCollector.*;
import java.util.ArrayList;

public class Distribution extends EpiscopalObject {
    private DistributionType type;
    private int numberOfElements;
    private Cell[] elements;

    public Distribution(DistributionType type, int  numberOfElements, Cell[] elements) {
        this.type = type;
        this.numberOfElements = numberOfElements;
        this.elements = elements;
    }

    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks = new ArrayList<>();

        Tag tag = new Tag(DataType.DISTRIB);
        Data type = new Data(this.type);
        Data numberOfElements = new Data(this.numberOfElements);

        tag.addEntry(type);
        tag.addEntry(numberOfElements);

        blocks.add(tag);
        blocks.add(type);
        blocks.add(numberOfElements);

        for (int i = 0; i < this.numberOfElements; i++) {
            Data element = (Data) ((Tag) elements[i]).getEntries().get(0);

            tag.addEntry(element);
        }

        return blocks;
    }

    @Override
    public int getSize() {
        return MIN_SIZE + numberOfElements;
    }
}
