package GarbageCollector;

import org.junit.Before;
import org.junit.Test;

public class TreadmillTest {
    private Treadmill collector;

    @Before
    public void setup() {
        collector = new Treadmill();
    }

    @Test
    public void intAllocation() {
        Tag int1 = collector.allocate();

        assert(collector.free == int1);
        assert(int1.getPointer() instanceof Data);
        assert(int1.getPointer().getNext() == collector.free);
    }

    @Test
    public void doubleLink() {
        Tag int1 = collector.allocate();

        assert(int1.getNext().getPrev() == int1);
    }
}