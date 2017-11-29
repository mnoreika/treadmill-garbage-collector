package GarbageCollector;

public abstract class Cell {
    private static boolean global = true;

    private Cell next;
    private Cell prev;
    private boolean ecru;

    public Cell() {
        this.next = null;
        this.prev = null;
        this.ecru = false;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    public void setPrev(Cell prev) {
        this.prev = prev;
    }

    public void setToNotEcru() { this.ecru = true; }

    public Cell getNext() {
        return next;
    }

    public Cell getPrev() {
        return prev;
    }

    public boolean isEcru() {
        return ecru ^ global;
    }

    public static void flipGlobal() {
        global = !global;
    }

    public static void setGlobal() {
        global = true;
    }

}
