public class Tag extends Cell {
    private DataType type;
    private Cell pointer;

    public Tag() {
        super();

        type = DataType.INT;
    }

    public void setPointer(Cell cell) {
        pointer = cell;
    }

    public Cell getPointer() {
        return pointer;
    }
}
