package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.DataType;
import GarbageCollector.Tag;
import GarbageCollector.Data;

import java.util.ArrayList;

public class Function extends EpiscopalObject {
    private int numberOfParameters;
    private Cell[] parameters;

    public Function(int numberOfParameters, Cell[] parameters) {
        this.numberOfParameters = numberOfParameters;
        this.parameters = parameters;
    }

    @Override
    public ArrayList<Cell> allocate() {
        ArrayList<Cell> blocks= new ArrayList<>();

        Tag tag = new Tag(DataType.FUNCTION);
        blocks.add(tag);

        for (int i = 0; i < numberOfParameters; i++) {
            Data parameter = new Data(new Indirection(parameters[i]));
            blocks.add(parameter);
            tag.addEntry(parameter);
        }

        return blocks;
    }
}
