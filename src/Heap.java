public class Heap {

    public Tag allocateTag() {
        return new Tag();
    }

    public Cell allocateInt() {
        return new Integer(2);
    }
}
