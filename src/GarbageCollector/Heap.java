package GarbageCollector;

import EpiscopalObjects.EpiscopalObject;

import java.util.ArrayList;

public class Heap {

    public ArrayList<Cell> allocateObject(EpiscopalObject object) {
        return object.allocate();
    }

}
