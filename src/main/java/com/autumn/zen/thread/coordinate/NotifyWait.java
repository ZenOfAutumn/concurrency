package com.autumn.zen.thread.coordinate;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 **/
public class NotifyWait {

  private Object lock = new Object();

  public static void main(String[] args) {

    NotifyWait notifyWait = new NotifyWait();
    Thread waiter = new Thread(new Runnable() {
      @Override
      public void run() {
        notifyWait.waitForNotify();
      }
    });

    Thread notify = new Thread(new Runnable() {
      @Override
      public void run() {
        notifyWait.notifyAllWaiter();;
      }
    });

    waiter.start();
    notify.start();

  }

  public void waitForNotify() {
    synchronized (lock) {
      try {
        System.out.println(Thread.currentThread().getName() + " begin to wait");

        lock.wait();
        System.out.println(Thread.currentThread().getName() + " wake up");

      } catch (InterruptedException e) {
        System.out.println(Thread.currentThread().getName() + " interrupt");

      }

    }
  }

  public void notifyAllWaiter() {

    synchronized (lock) {
      try {
        System.out.println(Thread.currentThread().getName() + " will notify");
        lock.notifyAll();
        System.out.println(Thread.currentThread().getName() + " have notify all");
        // relay lock time
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        System.out.println(Thread.currentThread().getName() + " interrupt");

      }
    }

  }
}
