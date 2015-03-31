package org.roaringbitmap.aggregation.newand.bestcase;

import org.openjdk.jmh.annotations.*;
import org.roaringbitmap.RoaringBitmap;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class RoaringBitmapBenchmark {

    private RoaringBitmap bitmap1;
    private RoaringBitmap bitmap2;

    @Setup
    public void setup() {
        bitmap1 = new RoaringBitmap();
        bitmap2 = new RoaringBitmap();
        int k = 1 << 16;
        int i = 0;
        for(; i < 10000; ++i) {
            bitmap1.add(i * k);
        }
        for(; i < 10050; ++i) {
            bitmap2.add(i * k);
            bitmap1.add(i * k + 13);
        }
        for(; i < 20000; ++i) {
            bitmap2.add(i * k);
        }
        bitmap1.add(i * k);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void and() {
        RoaringBitmap.and(bitmap1, bitmap2);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void inplace_and() {
        bitmap1.and(bitmap2);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void newand() {
        RoaringBitmap.newand(bitmap1, bitmap2);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void inplace_newand() {
        bitmap1.newand(bitmap2);
    }

}
