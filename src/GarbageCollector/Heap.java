package GarbageCollector;

import EpiscopalObjects.EpiscopalObject;

import java.util.ArrayList;

public class Heap {

    public ArrayList<Cell> allocateCells(EpiscopalObject object) {
        return object.allocate();
    }

    public ArrayList<Cell> reuseCells(EpiscopalObject object, ArrayList<Cell> cells) {
        return object.allocate();
    }
}
