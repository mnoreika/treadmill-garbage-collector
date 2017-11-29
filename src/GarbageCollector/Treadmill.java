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
        Cell allocatedTag = null;

        // No drop cells available
        if (free == bottom) {
            ArrayList<Cell> cells = heap.allocateCells(object);
            allocatedTag = cells.get(0);

            linkCells(cells);

             /* First allocation */
            if (scan == null) {
                scan = allocatedTag;
            }
            else {
                allocatedTag.setNext(scan);
                scan.setPrev(allocatedTag);
                scan = allocatedTag;
            }
        } else {
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
        collection();
    }

    private void collection() {
        for (Cell root : scope) {
            moveCellToGrey(root);
        }

        scan = scan.getPrev();

        while (scan != top) {
            scanCell(scan);

            scan = scan.getPrev();
        }

        flip();
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
        takeCellOut(cell);
        cell.setToNotEcru();

        cell.setNext(top);
        cell.setPrev(top.getPrev());
        top.getPrev().setNext(cell);
        top.setPrev(cell);
        top = cell;
    }

    private void takeCellOut(Cell cell) {
        cell.getPrev().setNext(cell.getNext());
        cell.getNext().setPrev(cell.getPrev());
    }

    private void flip() {
        Cell.flipGlobal();

        bottom = top;
        top = free;
        scan = free;
    }

}
