package edu.mit.broad.sting.atk.modules;

import edu.mit.broad.sam.SAMRecord;
import edu.mit.broad.sting.atk.ReadWalker;
import edu.mit.broad.sting.atk.LocusContext;

/**
 * Created by IntelliJ IDEA.
 * User: mdepristo
 * Date: Feb 22, 2009
 * Time: 3:22:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadWalkerTest implements ReadWalker<Integer, Integer> {
    long[] qualCounts = new long[100];

    public void initialize() {
        for ( int i = 0; i < this.qualCounts.length; i++ ) {
            this.qualCounts[i] = 0;
        }
    }

    public String walkerType() { return "ByRead"; }

    // Do we actually want to operate on the context?
    public boolean filter(LocusContext context, SAMRecord read) {
        return true;    // We are keeping all the reads
    }

    // Map over the edu.mit.broad.sting.atk.LocusContext
    public Integer map(LocusContext context, SAMRecord read) {
        for ( byte qual : read.getBaseQualities() ) {
            //System.out.println(qual);
            this.qualCounts[qual]++;
        }
        //System.out.println(read.getReadName());
        return 1;
    }

    // Given result of map function
    public Integer reduceInit() { return 0; }
    public Integer reduce(Integer value, Integer sum) {
        return value + sum;
    }

    public void onTraveralDone() {
        for ( int i = 0; i < this.qualCounts.length; i++ ) {
            System.out.printf("%3d : %10d%n", i, this.qualCounts[i]);
        }
    }
}
