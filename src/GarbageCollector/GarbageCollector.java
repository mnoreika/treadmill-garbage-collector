package GarbageCollector;

import EpiscopalObjects.EpiscopalObject;

public abstract class GarbageCollector {
    protected Heap heap;

    public GarbageCollector() {
        heap = new Heap();
    }

    public abstract Cell allocate(EpiscopalObject object);

    public abstract void drop(Cell cell);
}
