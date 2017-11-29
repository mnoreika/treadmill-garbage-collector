package EpiscopalObjects;

import GarbageCollector.*;
import java.util.ArrayList;

public class PDistribution extends EpiscopalObject {
    private DistributionType type;
    private int numberOfElements;
    private int numberOfParameters;
    private Cell[] elements;
    private Cell[] parameters;

    public PDistribution(DistributionType type, int numberOfParameters, int  numberOfElements, Cell[] parameters, Cell[] elements) {
        this.type = type;
        this.numberOfElements = numberOfElements;
        this.numberOfParameters = numberOfParameters;
        this.parameters = parameters;
        this.elements = elements;
    }

    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks = new ArrayList<>();

        Tag tag = new Tag(DataType.PDISTRIB);
        Data type = new Data(this.type);
        Data numberOfParameters = new Data(this.numberOfParameters);
        Data numberOfElements = new Data(this.numberOfElements);

        tag.addEntry(type);

        blocks.add(tag);
        blocks.add(type);

        tag.addEntry(numberOfParameters);
        blocks.add(numberOfParameters);
        for (int i = 0; i < this.numberOfParameters; i++) {
            Data parameter = (Data) ((Tag) parameters[i]).getEntries().get(0);

            tag.addEntry(parameter);
        }

        tag.addEntry(numberOfElements);
        blocks.add(numberOfElements);
        for (int i = 0; i < this.numberOfElements; i++) {
            Data element = (Data) ((Tag) elements[i]).getEntries().get(0);

            tag.addEntry(element);
        }

        return blocks;
    }

    @Override
    public int getSize() {
        return MIN_SIZE + numberOfParameters + numberOfElements;
    }
}
