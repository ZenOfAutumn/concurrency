package com.autumn.zen.thread.cancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 **/
public class InterruptCancel extends Thread {

  private final boolean block;

  private final BlockingQueue<BigInteger> queue;

  InterruptCancel(BlockingQueue<BigInteger> queue, boolean blcok) {
    this.queue = queue;
    this.block = blcok;
  }

  public static void main(String[] args) throws InterruptedException {

    System.out.println("************************block interrupt********************************");
    InterruptCancel blockingInterrupt = new InterruptCancel(new LinkedBlockingQueue<>(1), true);
    blockingInterrupt.start();
    TimeUnit.MILLISECONDS.sleep(1000);
    System.out.println(blockingInterrupt.getState());
    blockingInterrupt.cancel();
    TimeUnit.SECONDS.sleep(3);
    System.out.println(blockingInterrupt.getState());
    System.out.println("thread is alive: " + blockingInterrupt.isAlive());

    System.out.println("************************flag interrupt*************************");
    InterruptCancel flagInterrupt = new InterruptCancel(new LinkedBlockingQueue<>(1000), false);
    flagInterrupt.start();
    TimeUnit.MILLISECONDS.sleep(1000);
    System.out.println(flagInterrupt.getState());
    flagInterrupt.cancel();
    TimeUnit.SECONDS.sleep(3);
    System.out.println(flagInterrupt.getState());
    System.out.println("thread is alive: " + blockingInterrupt.isAlive());


  }

  @Override
  public void run() {

    try {
      BigInteger p = BigInteger.ONE;
      while (!Thread.currentThread().isInterrupted()) {
        if (block) {
          queue.put(p = p.nextProbablePrime());
        }
        int j = 0;
        for (int i = 0; i < 1000000; i++) {
          j += i;
        }
      }

      System.out.println(
        "thread interrupt when running, interrupt flag: " + Thread.currentThread().isInterrupted());
      Thread.interrupted();
      System.out.println(
        "thread interrupted after interrupt, interrupt flag: " + Thread.currentThread()
          .isInterrupted());

    } catch (InterruptedException e) {
      System.out
        .println("thread interrupt when blocking, interrupt flag: " + Thread.currentThread()
          .isInterrupted());
    }

  }

  public void cancel() {
    interrupt();
  }
}
