package GarbageCollector;

import EpiscopalObjects.Indirection;
import EpiscopalObjects.Integer;
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
        Cell int1 = collector.allocate(new Integer(2));
        Cell int2 = collector.allocate(new Integer(4));
        Cell ind = collector.allocate(new Indirection(int2));

        assert(collector.scan == int1);
        assert(collector.scan.getNext() instanceof Data);
        assert(collector.scan.getNext() == ((Tag) int1).getEntries().get(0));

        collector.drop(int2);

        collector.printTread();

        collector.collection();
//
        collector.printTread();

        collector.flip();

        collector.printTread();

    }

//    @Test
//    public void doubleLink() {
//        Tag int1 = collector.allocate();
//
//        assert(int1.getNext().getPrev() == int1);
//    }
}