import GarbageCollector.Data;
import GarbageCollector.Tag;
import GarbageCollector.Threadmill;
import org.junit.Before;
import org.junit.Test;

public class ThreadmillTest {
    private Threadmill collector;

    @Before
    public void setup() {
        collector = new Threadmill();
    }

    @Test
    public void intAllocation() {
        Tag int1 = collector.allocate();

        assert(collector.scan == int1);
        assert(int1.getPointer() instanceof Data);
        assert(int1.getPointer().getNext() == collector.free);
    }

    @Test
    public void doubleLink() {
        Tag int1 = collector.allocate();

        assert(int1.getNext().getPrev() == int1);
    }
}