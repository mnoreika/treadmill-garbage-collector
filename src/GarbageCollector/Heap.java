package GarbageCollector;

import java.util.ArrayList;

public class Heap {

    public ArrayList<Cell> allocateObject(Data object) {
        return object.allocate();
    }

}
