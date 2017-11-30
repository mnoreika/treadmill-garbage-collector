package EpiscopalObjects;

import GarbageCollector.Cell;
import GarbageCollector.Treadmill;

public class Benchmark {
    public static void main(String[] args) {
        Treadmill collector = new Treadmill();

        Cell int1 = collector.allocate(new Integer(2));
        Cell int2 = collector.allocate(new Integer(3));
        Cell int3 = collector.allocate(new Integer(3));

        collector.printTread();

        collector.free(int1);

        collector.printTread();

        collector.collectGarbage();

        collector.printTread();

        Cell int4 = collector.allocate(new Distribution(DistributionType.CUSTOM, 1, new Cell[] {int2}));

//        collector.free(int2);
//
        collector.printTread();
//
//        Cell int3 = collector.allocate(new Integer(4));
////
//        collector.printTread();

    }
}
