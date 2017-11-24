import Variables.Colours;
import Variables.Variable;

public class Cell {
    private Variable data;
    private Cell next;
    private Cell prev;
    private Colours colour;

    public Cell(Variable data) {
        this.data = data;
        this.next = null;
        this.prev = null;
        this.colour = Colours.BLACK;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    public void setPrev(Cell prev) {
        this.prev = prev;
    }

    public Cell getNext() {
        return next;
    }

    public Cell getPrev() {
        return prev;
    }


}
