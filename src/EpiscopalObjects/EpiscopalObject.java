package EpiscopalObjects;

import GarbageCollector.Cell;

import java.util.ArrayList;

public abstract class EpiscopalObject {
    protected final int MIN_SIZE = 2;

    public abstract ArrayList<Cell> allocate();

    public abstract int getSize();
}
