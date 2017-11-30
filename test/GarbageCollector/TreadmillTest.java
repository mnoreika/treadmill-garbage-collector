package GarbageCollector;

import EpiscopalObjects.*;
import EpiscopalObjects.Integer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TreadmillTest {
    private Treadmill collector;

    @Before
    public void setup() {
        collector = new Treadmill();

        // Reset the global bit
        Cell.setGlobal();
    }

    @Test
    public void varAllocation() {
        System.out.println("--- varAllocation test ---");

        Cell int1 = collector.initialize(new Integer(2));

        // Check if objects are linked together properly
        assertTrue(collector.scan == int1);
        assertTrue(collector.scan.getNext() instanceof Data);
        assertTrue(collector.scan.getNext() == ((Tag) int1).getEntries().get(0));

        // Check if pointers are pointing to the right cell
        assertTrue(collector.scan == collector.bottom);
        assertTrue(collector.scan == collector.top);
        assertTrue(collector.scan == collector.free);

        // Check if the list is roundly linked
        assertTrue(collector.scan.getNext().getNext() == collector.scan);

        collector.printTread();

        System.out.println();
    }

    @Test
    public void multVarAllocation() {
        System.out.println("--- multVarAllocation test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(42));

        // Check if objects are linked together properly
        assertTrue(int1.getNext().getNext() == int2);

        // Check if the list is roundly linked
        assertTrue(collector.scan.getNext().getNext() == collector.scan.getPrev().getPrev());

        collector.printTread();

        System.out.println();
    }

    @Test
    public void singleVarCollection() {
        System.out.println("--- singleVarCollection test ---");

        Cell int1 = collector.initialize(new Integer(2));

        collector.printTread();
        collector.scan();

        assertTrue(collector.free == collector.bottom);

        assertTrue(collector.free == collector.top);
        assertTrue(collector.free == collector.scan);

        System.out.println();
    }

    @Test
    public void multiVarCollection() {
        System.out.println("--- multiVarCollection test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(4));

        collector.printTread();
        collector.collectGarbage();

        assertTrue(collector.free == collector.bottom);

        assertTrue(collector.free == collector.top);
        assertTrue(collector.free == collector.scan);

        System.out.println();
    }

    @Test
    public void multiVarDropRefCollection() {
        System.out.println("--- multiVarDropCollection test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(4));

        collector.dropRoot(int2);

        collector.printTread();
        collector.scan();

        assertTrue(collector.bottom == int2);

        collector.flip();

        collector.printTread();

        assertTrue(collector.free == int2);
        assertTrue(collector.free.getNext() == ((Tag) int2).getEntries().get(0));

        System.out.println();
    }

    @Test
    public void dropVarPointerByIndirectionCollection() {
        System.out.println("--- dropVarPointerByIndirectionCollection test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(4));
        Cell ind = collector.initialize(new Indirection(int2));

        collector.dropRoot(int2);

        collector.printTread();
        collector.scan();

        assertTrue(collector.bottom == int2);

        collector.flip();

        assertTrue(collector.free == int2);
        assertTrue(collector.free.getNext() == collector.bottom);

        System.out.println();
    }

    @Test
    public void distAllocationAndCollection() {
        System.out.println("--- distAllocationAndCollection test ---");

        Cell el1 = collector.initialize(new Integer(5));
        Cell el2 = collector.initialize(new Integer(10));

        Cell[] elements = {el1, el2};

        Cell customDist = collector.initialize(new Distribution(DistributionType.CUSTOM, 2, elements));

        collector.printTread();

        collector.scan();

        collector.printTread();

        assertTrue(collector.scan == collector.bottom);
        assertTrue(collector.scan == collector.top);
        assertTrue(collector.scan == collector.free);

        System.out.println();
    }

    @Test
    public void distCollection() {
        System.out.println("--- distCollection test ---");

        Cell el1 = collector.initialize(new Integer(5));
        Cell el2 = collector.initialize(new Integer(10));

        Cell[] elements = {el1, el2};

        Cell customDist = collector.initialize(new Distribution(DistributionType.CUSTOM, 2, elements));

        collector.dropRoot(customDist);

        collector.printTread();

        collector.scan();

        collector.flip();

        collector.printTread();

        assertTrue(collector.free != collector.bottom);
        assertTrue(collector.free == customDist);

        System.out.println();
    }

    @Test
    public void dropElementsInDist() {
        System.out.println("--- dropElementsInDist test ---");

        Cell el1 = collector.initialize(new Integer(5));
        Cell el2 = collector.initialize(new Integer(10));

        Cell[] elements = {el2, el1};

        Cell customDist = collector.initialize(new Distribution(DistributionType.CUSTOM, 2, elements));

        collector.dropRoot(el2);

        collector.printTread();

        collector.scan();

        collector.printTread();

        collector.flip();

        collector.printTread();

        assertTrue(collector.free == el2);
        assertTrue(el2.getNext() == collector.bottom);

        System.out.println();
    }

    @Test
    public void nestedPointers() {
        System.out.println("--- dropElementsInDist test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell ind1 = collector.initialize(new Indirection(int1));
        Cell ind2 = collector.initialize(new Indirection(ind1));

        collector.dropRoot(int1);
        collector.dropRoot(ind1);

        collector.printTread();

        collector.scan();

        collector.printTread();

        collector.flip();

        collector.printTread();

        // Check if integer 2 is still accessible
        assertTrue(collector.bottom instanceof Data);
        assertTrue((((Data) collector.bottom).getData()).toString().equals("2"));
        System.out.println();
    }

    @Test
    public void dropAndCollectPDistribution() {
        System.out.println("--- dropAndCollectPDistribution test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(10));
        Cell int3 = collector.initialize(new Integer(42));
        Cell int4 = collector.initialize(new Integer(124));
        Cell int5 = collector.initialize(new Integer(50));
        Cell int6 = collector.initialize(new Integer(77));

        Cell pdist = collector.initialize(new PDistribution(DistributionType.CUSTOM,
                3, 3, new Cell[] { int1, int2, int3 }, new Cell[] {int4, int5, int6}));

        collector.dropRoot(pdist);

        collector.printTread();
        collector.scan();

        collector.flip();

        collector.printTread();

        assertTrue(collector.free != collector.bottom);
        assertTrue(collector.free == pdist);

        System.out.println();
    }

    @Test
    public void dropRefAndAllocateFromFree() {
        System.out.println("--- dropRefAndAllocateFromFree test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(10));

        collector.dropRoot(int2);

        collector.printTread();
        collector.scan();

        collector.flip();

        assertTrue(collector.free == int2);

        collector.printTread();

        Cell newInt2 = collector.initialize(new Integer(12));

        collector.printTread();

        assertTrue(collector.free.getPrev().getPrev() == newInt2);

        System.out.println();
    }

    @Test
    public void variedHeap() {
        System.out.println("--- variedHeap test ---");

        Cell int1 = collector.initialize(new Integer(2));
        Cell int2 = collector.initialize(new Integer(10));
        Cell int3 = collector.initialize(new Integer(42));
        Cell int4 = collector.initialize(new Integer(124));
        Cell int5 = collector.initialize(new Integer(50));
        Cell int6 = collector.initialize(new Integer(77));

        Cell dist1 = collector.initialize(new Distribution(DistributionType.BETA, 2, new Cell[]{int1, int6}));
        Cell dist2 = collector.initialize(new Distribution(DistributionType.CUSTOM, 4, new Cell[]{int1, int2, int3, int4}));
        Cell indi1 = collector.initialize(new Indirection(int5));

        collector.dropRoot(dist2);
        collector.dropRoot(int1);

        collector.printTread();

        collector.scan();

        collector.flip();

        collector.printTread();

        int freeCounter = 0;
        Cell iterator = collector.free;
        while (iterator != collector.bottom) {
            freeCounter++;
            iterator = iterator.getNext();
        }

        assertTrue(freeCounter == 4);

        System.out.println();
    }


}