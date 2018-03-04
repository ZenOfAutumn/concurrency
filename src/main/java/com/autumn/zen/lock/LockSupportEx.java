package com.autumn.zen.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Lock Support Example
 **/
public class LockSupportEx {

  public static void main(String[] args) throws InterruptedException {

    Thread t = new LockSupportThread();
    t.start();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("thread status: " + t.getState());
    TimeUnit.SECONDS.sleep(1);
    t.interrupt();
    TimeUnit.SECONDS.sleep(1);
    System.out.println("thread status: " + t.getState());

  }

  static class LockSupportThread extends Thread {

    @Override
    public void run() {
      LockSupport.park();
      System.out.println("resume from park by interrupt, interrupted: " + Thread.interrupted());

    }
  }
}
