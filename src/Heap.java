import Variables.Variable;

public class Heap {
    private GarbageCollector collector;

    public Heap(GarbageCollector collector) {
        this.collector = collector;
    }

    public Cell allocate(Variable var) {
        return collector.allocate(var);
    }

    public void free(Variable var) {
        collector.free(var);
    }

}
