package com.autumn.zen.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierEx {

  public static void main(String[] args) throws InterruptedException{

    ExecutorService executorService = Executors.newFixedThreadPool(7);
    CyclicBarrier barrier = new CyclicBarrier(7);
    List<FollowerTask> followerTasks = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      FollowerTask followerTask = new FollowerTask(barrier);
      followerTasks.add(followerTask);
      executorService.submit(followerTask);
    }
    executorService.submit(new LeaderTask(barrier, followerTasks));

    while (!LeaderTask.endFlag) {
      Thread.sleep(300);
      System.out.println("waiting num: " + barrier.getNumberWaiting());
    }
    executorService.shutdown();

  }

  static class FollowerTask implements Runnable {

    private volatile int retValue;

    private CyclicBarrier barrier;

    FollowerTask(CyclicBarrier barrier) {
      this.barrier = barrier;
    }

    public int getRetValue() {
      return retValue;
    }

    @Override
    public void run() {
      try {
        System.out.println(Thread.currentThread().getName() + " begin to work ");
        Thread.sleep(4000);
        retValue = new Random().nextInt(10);
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  static class LeaderTask implements Runnable {

    public static boolean endFlag = false;

    private final CyclicBarrier barrier;

    private final List<FollowerTask> followerTasks;

    LeaderTask(CyclicBarrier barrier, List<FollowerTask> followerTasks) {
      this.barrier = barrier;
      this.followerTasks = followerTasks;
    }

    @Override
    public void run() {
      try {
        System.out.println("barrier parts num: " + barrier.getParties());
        Thread.sleep(8000);
        barrier.await();
        int sum = 0;
        for (FollowerTask followerTask : followerTasks) {
          sum += followerTask.getRetValue();
        }
        System.out.println("sum: " + sum);
        barrier.reset();
        endFlag = true;
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }

    }
  }
}
