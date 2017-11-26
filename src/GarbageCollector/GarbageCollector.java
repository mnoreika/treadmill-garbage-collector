package GarbageCollector;

public abstract class GarbageCollector {
    protected Heap heap;

    public GarbageCollector() {
        heap = new Heap();
    }

    public abstract Tag allocate();

    public abstract void free(Cell cell);
}
