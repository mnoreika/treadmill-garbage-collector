package GarbageCollector;

import java.util.ArrayList;

public class Threadmill extends GarbageCollector {
    protected Cell bottom;
    protected Cell top;
    protected Cell scan;
    protected Cell free;
    protected ArrayList<Cell> scope;

    public Threadmill() {
        super();

        bottom = null;
        top = null;
        scan = null;
        free = null;

        scope = new ArrayList<Cell>();
    }

    @Override
    public Tag allocate() {
        Tag allocatedCell = null;

        if (free == bottom) {
//            flipCells();

            Tag tag = heap.allocateTag();
            Data data = heap.allocateInt();

             /* First allocation */
            if (scan == null) {
                scan = tag;
                data.setNext(free);
                data.setPrev(tag);
                scan.setNext(data);
                scan.setPrev(top);
            }
            else {
                tag.setNext(data);
                tag.setPrev(scan.getPrev());
                data.setNext(scan);
                data.setPrev(tag);
                scan.setPrev(data);
                scan = tag;
            }

            tag.setPointer(data);
            allocatedCell = tag;
        } else {
            allocatedCell = new Tag();
            free = free.getNext();

            if (free == bottom) {
                flipCells();
            }
        }

        scope.add(allocatedCell);
        return allocatedCell;
    }

    @Override
    public void free(Cell cell) {
        scope.remove(cell);
        recollection();
    }

    private void recollection() {
        scan = scan.getNext();

        while (scan != top) {
            scanCell(scan);

            scan = scan.getNext();
        }
    }

    private void scanCell(Cell cell) {
        if (cell instanceof Tag) {
            Cell data = ((Tag) cell).getPointer();

            if (data.getColour() == Colour.ECRU) {
                moveCellToGrey(cell);
            }
        }
    }

    private void moveCellToGrey(Cell cell) {
        cell.setColour(Colour.GREY);
        cell.setNext(top);
        cell.setPrev(top.getPrev());
        top.setPrev(cell);
        top = cell;
    }

    private void flipCells() {
        Cell iterator = bottom;

        /* Flip cell colours */
        while (iterator != top) {
            iterator.setColour(Colour.WHITE);
            iterator = iterator.getNext();
        }

        iterator = scan;
        while (iterator != free) {
            iterator.setColour(Colour.ECRU);
            iterator = iterator.getNext();
        }

        Cell temp = top;
        top = bottom;
        bottom = temp;

        for (Cell root : scope) {
            root.setColour(Colour.GREY);
        }
    }

}
