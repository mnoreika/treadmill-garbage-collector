package GarbageCollector;

import EpiscopalObjects.EpiscopalObject;
import java.util.ArrayList;

public class Treadmill extends GarbageCollector {
    protected Cell bottom;
    protected Cell top;
    protected Cell scan;
    protected Cell free;
    protected ArrayList<Cell> scope;

    public Treadmill() {
        super();

        bottom = null;
        top = null;
        scan = null;
        free = null;

        scope = new ArrayList<Cell>();
    }

    @Override
    public Cell allocate(EpiscopalObject object) {
//        if (free == bottom) {
//            collectGarbage();
//        }

        System.out.println("p");
        return initialize(object);
    }

    @Override
    public void free(Cell cell) {
        dropRoot(cell);
    }

    @Override
    public void collectGarbage() {
        if (free != null) {
            System.out.println("yo");
            scan();
            flip();
        }
    }

    public void printTread() {
        Cell iterator = bottom;
        boolean cycleFinished = false;

        while (!cycleFinished) {
            if (iterator == free) {
                System.out.print("|F|");
            }
            if (iterator == bottom) {
                System.out.print("|B|");
            }

            if (iterator == top) {
                System.out.print("|T|");
            }

            if (iterator == scan) {
                System.out.print("|S|");
            }

            System.out.print(iterator);
            if (iterator != null)
                iterator = iterator.getNext();

            if (iterator == bottom) {
                cycleFinished = true;
            }
        }

        System.out.println();
    }

    protected Cell initialize(EpiscopalObject object) {
        Cell allocatedTag = null;

        // No free cells available
        if (free == bottom) {
            ArrayList<Cell> cells = heap.allocateCells(object);
            allocatedTag = cells.get(0);
            Cell lastCell = cells.get(cells.size() - 1);

            linkCells(cells);

             /* First allocation */
            if (scan == null) {
                setUpInitialCells(allocatedTag, lastCell);
            }
            else {
                addNewCells(allocatedTag, lastCell);
            }

        } else {
            /* Reallocating free cells */
            allocatedTag = reallocateFreeCells(object);
        }

        scope.add(allocatedTag);
        return allocatedTag;
    }

    protected void dropRoot(Cell cell) {
        scope.remove(cell);
    }

    protected void scan() {
        for (Cell root : scope) {
            moveRoot(root);
            printTread();
        }

        boolean scanFinished = false;
        while (!scanFinished) {
            if (scan != top) {
                scan = scan.getPrev();

                scanCell(scan);
            }
            else
                scanFinished = true;
        }
    }

    protected void flip() {
        Cell.flipGlobal();

        bottom = top;
        top = free;
        scan = free;
    }

    private void setUpInitialCells(Cell allocatedTag, Cell lastCell) {
        scan = allocatedTag;
        top = scan;
        bottom = scan;
        free = scan;

        lastCell.setNext(scan);
        scan.setPrev(lastCell);
    }

    private Cell reallocateFreeCells(EpiscopalObject object) {
        ArrayList<Cell> freeCells = getFreeCells(object);
        ArrayList<Cell> cells = null;

        if (freeCells != null) {
            // Remove used dropRoot blocks
            for (Cell freeCell : freeCells) {
                takeCellOut(freeCell);
            }

            cells = heap.reuseCells(object, freeCells);
        } else {
            cells = heap.allocateCells(object);
        }

        for (Cell cell : cells) {
            moveCellsToEcru(cell);
        }

        return cells.get(0);
    }

    private void moveCellsToEcru(Cell cell) {
        cell.setNext(bottom);
        cell.setPrev(bottom.getPrev());
        bottom.getPrev().setNext(cell);
        bottom.setPrev(cell);
        bottom = cell;
    }

    private ArrayList<Cell> getFreeCells(EpiscopalObject object) {
        int objectCellSize = object.getSize();
        System.out.println(objectCellSize);
        int freeCount = 0;

        ArrayList<Cell> freeCells = new ArrayList<Cell>();
        boolean enoughFreeCells = false;

        Cell iterator = free;
        while (iterator != bottom && freeCount < objectCellSize) {
            freeCells.add(iterator);
            freeCount++;

            iterator = iterator.getNext();
            if (freeCount == objectCellSize) {
                enoughFreeCells = true;
            }
        }

        if (enoughFreeCells)
            return freeCells;
        else
            return null;
    }

    private void addNewCells(Cell allocatedTag, Cell lastCell) {
        free.getPrev().setNext(allocatedTag);
        allocatedTag.setPrev(free.getPrev());
        free.setPrev(lastCell);
        lastCell.setNext(free);

    }

    private void linkCells(ArrayList<Cell> cells) {
        for (int i = 0; i < cells.size(); i++) {
            Cell current = cells.get(i);

            if (i == 0) {
                current.setPrev(null);

                if (cells.size() > 1)
                    current.setNext(cells.get(i + 1));
                else
                    current.setNext(null);
            }
            else if (i == (cells.size() - 1)) {
                current.setPrev(cells.get(i - 1));
                current.setNext(null);
            }
            else {
                current.setPrev(cells.get(i - 1));
                current.setNext(cells.get(i + 1));
            }
        }
    }

    private void scanCell(Cell cell) {
        if (cell instanceof Tag) {
            for (Cell children : ((Tag) cell).getEntries()) {
                if (children.isEcru())
                    moveCellToGrey(children);
            }
        }
    }

    private void moveCellToGrey(Cell cell) {
        if (cell.getPrev() != null)
            takeCellOut(cell);

        /* Handle edge case where after move top, bottom and free point to the same cell */
        if (top == bottom && top != scan) {
            bottom = cell;
        }

        if (top == free && top != scan) {
            free = cell;
        }

        cell.setToNotEcru();

        cell.setNext(top);
        cell.setPrev(top.getPrev());
        top.getPrev().setNext(cell);
        top.setPrev(cell);

        if (bottom != top) {
            top = cell;
        }
    }

    private void moveRoot(Cell cell) {
        takeCellOut(cell);

        cell.setToNotEcru();

        cell.setNext(top);
        cell.setPrev(top.getPrev());
        top.getPrev().setNext(cell);
        top.setPrev(cell);

        top = cell;
    }

    private void takeCellOut(Cell cell) {
        if (cell == top)
            top = top.getNext();
        if (cell == bottom)
            bottom = bottom.getNext();
        if (cell == scan)
            scan = scan.getNext();
        if (cell == free)
            free = free.getNext();

        cell.getPrev().setNext(cell.getNext());
        cell.getNext().setPrev(cell.getPrev());
    }
}
