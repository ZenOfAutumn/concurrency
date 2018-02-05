package com.autumn.zen.architecture.fork_join.caculator;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 **/
public class App {

  public static final Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    Random random = new Random();
    long[] nums = new long[1000000];
    for (int i = 0; i < 1000000; i++) {
      nums[i] = random.nextInt(1000);
    }

    Long start = System.nanoTime();
    long total = 0;
    for (int i = 0; i < nums.length; i++) {
      total += nums[i];
    }
    Long end = System.nanoTime();
    LOG.info("plain loop sum cost:{} ns", end - start);

    start = System.nanoTime();
    ForkJoinCalculator calculator = new ForkJoinCalculator();
    long sum = calculator.sum(nums);
    end = System.nanoTime();
    LOG.info("fork join sum cost:{} ns", end - start);
    LOG.info("total:{},sum:{}", total, sum);

  }

}
