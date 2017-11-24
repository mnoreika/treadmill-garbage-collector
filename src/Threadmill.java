import Variables.Variable;

public class Threadmill extends GarbageCollector {
    private Cell bottom;
    private Cell top;
    private Cell scan;
    private Cell free;

    public Threadmill() {
        bottom = null;
        top = null;
        scan = null;
        free = null;
    }

    @Override
    public Cell allocate(Variable var) {
        /* First allocation */
        if (free == null) {
            Cell cell = new Cell(var);

            if (scan == null) {
                scan = cell;
            }
            else {
                scan.setNext(cell);
            }
        }

        return null;
    }

    @Override
    public void free(Variable var) {
        if ()
    }
}
