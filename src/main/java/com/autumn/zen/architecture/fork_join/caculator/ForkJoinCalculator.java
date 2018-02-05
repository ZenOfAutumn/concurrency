package com.autumn.zen.architecture.fork_join.caculator;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 *
 **/
public class ForkJoinCalculator implements Calculator {

  private ForkJoinPool forkJoinPool;

  public ForkJoinCalculator() {
    this.forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
  }

  @Override
  public long sum(long[] nums) {
    return forkJoinPool.invoke(new SumTask(nums, 0, nums.length - 1));
  }

  private static class SumTask extends RecursiveTask<Long> {

    private long[] nums;

    private int from;

    private int to;

    SumTask(long[] nums, int from, int to) {
      this.from = from;
      this.to = to;
      this.nums = nums;
    }

    @Override
    protected Long compute() {
      if (to - from < 1000) {
        long total = 0;
        for (int i = from; i <= to; i++) {
          total += nums[i];
        }
        return total;
      } else {
        int mid = (from + to) / 2;
        SumTask leftTask = new SumTask(nums, from, mid);
        SumTask rightTask = new SumTask(nums, mid + 1, to);
        leftTask.fork();
        rightTask.fork();
        return leftTask.join() + rightTask.join();

      }

    }
  }
}
