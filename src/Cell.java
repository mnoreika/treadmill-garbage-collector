public abstract class Cell {
    private Cell next;
    private Cell prev;
    private Colour colour;

    public Cell() {
        this.next = null;
        this.prev = null;
        this.colour = Colour.BLACK;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    public void setPrev(Cell prev) {
        this.prev = prev;
    }

    public void setColour(Colour coulour) { this.colour = colour; }

    public Cell getNext() {
        return next;
    }

    public Cell getPrev() {
        return prev;
    }

    public Colour getColour() {
        return colour;
    }
}
