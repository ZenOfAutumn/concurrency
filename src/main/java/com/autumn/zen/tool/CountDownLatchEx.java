package com.autumn.zen.tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 **/
public class CountDownLatchEx {

  public static void main(String[] args) {

    final int THREAD_NUM = Runtime.getRuntime().availableProcessors();
    CountDownLatch startGate = new CountDownLatch(1);
    CountDownLatch endGate = new CountDownLatch(THREAD_NUM);

    for (int i = 0; i < THREAD_NUM; i++) {

      Thread t = new Thread() {
        @Override
        public void run() {

          try {
            startGate.await();
            // mock task run
            System.out.println(Thread.currentThread().getName() + " begin to work");
            TimeUnit.SECONDS.sleep(10);
            System.out.println(Thread.currentThread().getName() + " work end");

          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            endGate.countDown();
          }
        }
      };
      t.start();
    }

    startGate.countDown();

    try {
      endGate.await();
      System.out.println("all thread done");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
