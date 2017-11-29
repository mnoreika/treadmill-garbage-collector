package GarbageCollector;

import EpiscopalObjects.EpiscopalObject;
import EpiscopalObjects.Indirection;
import com.sun.org.apache.xpath.internal.SourceTree;

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
        Cell allocatedTag = null;

        // No drop cells available
        if (free == bottom) {
            ArrayList<Cell> cells = heap.allocateCells(object);
            allocatedTag = cells.get(0);
            Cell lastCell = cells.get(cells.size() - 1);

            linkCells(cells);

             /* First allocation */
            if (scan == null) {
                scan = allocatedTag;
                top = scan;
                bottom = scan;
                free = scan;

                lastCell.setNext(scan);
                scan.setPrev(lastCell);
            }
            else {
                free.getPrev().setNext(allocatedTag);
                allocatedTag.setPrev(free.getPrev());
                free.setPrev(lastCell);
                lastCell.setNext(free);
            }
        } else {
            System.out.println("ALLOCATION!");
            int objectCellSize = object.getSize();
            int freeCount = 0;
            ArrayList<Cell> freeCells = new ArrayList<Cell>();
            boolean enoughFreeCells = false;

            Cell iterator = free;
            while (iterator != bottom && freeCount < objectCellSize) {
                freeCells.add(free);
                freeCount++;

                if (freeCount == objectCellSize) {
                    enoughFreeCells = true;

                    // Remove used drop blocks
                    free.getPrev().setNext(free.getNext());
                }

                free = free.getNext();
            }

            ArrayList<Cell> cells = null;

            if (enoughFreeCells) {
                cells = heap.reuseCells(object, freeCells);
            } else {
                cells = heap.allocateCells(object);
            }

            linkCells(cells);

            allocatedTag.setNext(scan);
            scan.setPrev(allocatedTag);
            scan = allocatedTag;
        }

        scope.add(allocatedTag);
        return allocatedTag;
    }

    private void linkCells(ArrayList<Cell> cells) {
        for (int i = 0; i < cells.size(); i++) {
            Cell current = cells.get(i);

            if (i == 0) {
                current.setPrev(null);
                current.setNext(cells.get(i + 1));
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

    @Override
    public void drop(Cell cell) {
        scope.remove(cell);
//        collection();
    }

    protected void collection() {
//        flip();

        for (Cell root : scope) {
            moveCellToGrey(root);
        }

        printTread();
        System.out.println("--------------------");

        scan = scan.getPrev();

        while (scan != top) {
            scanCell(scan);
            scan = scan.getPrev();

            if (scan == top)
                scanCell(scan);
        }

//        flip();
    }

    private void scanCell(Cell cell) {
        printTread();
        if (cell instanceof Tag) {
            for (Cell children : ((Tag) cell).getEntries()) {
                if (children.isEcru())
                    moveCellToGrey(children);
            }
        }

//        if (cell instanceof Data) {
//            Object data = ((Data) cell).getData();
//
//            if (data instanceof Cell) {
//                moveCellToGrey(cell);
//            }
//        }

        printTread();

    }

    private void moveCellToGrey(Cell cell) {
        takeCellOut(cell);

        printTread();

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

    protected void flip() {
        Cell.flipGlobal();

        bottom = top;
        top = free;
        scan = free;
    }

    protected void printTread() {
        int counter = 0;

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
            iterator = iterator.getNext();

            if (iterator == bottom) {
                cycleFinished = true;
            }


            if (counter == 100)
                break;

            counter++;
        }

        System.out.println();
    }

}
