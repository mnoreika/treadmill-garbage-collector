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
            Tag indirection = new Tag(DataType.IND);
            Data parameter = new Data(new Indirection(parameters[i]));

            blocks.add(indirection);
            blocks.add(parameter);

            indirection.addEntry(parameter);
            tag.addEntry(indirection);
        }

        tag.addEntry(numberOfElements);
        blocks.add(numberOfElements);
        for (int i = 0; i < this.numberOfElements; i++) {
            Tag indirection = new Tag(DataType.IND);
            Data element = new Data(new Indirection(elements[i]));

            blocks.add(indirection);
            blocks.add(element);

            indirection.addEntry(element);
            tag.addEntry(indirection);
        }

        return blocks;
    }
}
