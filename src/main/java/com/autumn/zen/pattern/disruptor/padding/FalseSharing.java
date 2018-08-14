package com.autumn.zen.pattern.disruptor.padding;

import com.autumn.zen.pattern.disruptor.padding.DisruptorPadding.RhsPadding;

public class FalseSharing implements Runnable {

  public final static int NUM_THREADS = 4; // change

  public final static long ITERATIONS = 500L * 1000L * 1000L;

  private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

  private static RhsPadding[] paddings = new RhsPadding[NUM_THREADS];

  static {
    for (int i = 0; i < longs.length; i++) {
      longs[i] = new VolatileLong();
      paddings[i] = new RhsPadding();
    }
  }

  private final boolean right;

  private final int arrayIndex;

  public FalseSharing(final int arrayIndex, final boolean right) {
    this.arrayIndex = arrayIndex;
    this.right = right;
  }

  public static void runTest(int threadNums, boolean right) throws InterruptedException {
    Thread[] threads = new Thread[threadNums];
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new FalseSharing(i, right));
    }
    for (Thread thread : threads) {
      thread.start();
    }
    for (Thread thread : threads) {
      thread.join();
    }
  }

  public static void main(String[] args) throws InterruptedException {

    for (int i = 1; i <= Runtime.getRuntime().availableProcessors(); i++) {
      final long start = System.nanoTime();
      runTest(i, true);
      System.out.println("core " + i + ":" + (System.nanoTime() - start));
    }

  }

  @Override
  public void run() {

    long i = ITERATIONS + 1;

    if (!right) {
      while (--i != 0) {

        longs[arrayIndex].value = i;
      }
    } else {
      while (--i != 0) {

        paddings[arrayIndex].value = i;
      }
    }
  }

  private static class VolatileLong {

    public volatile long value = 0L;

    public long p1, p2, p3, p4, p5, p6;
  }

}
