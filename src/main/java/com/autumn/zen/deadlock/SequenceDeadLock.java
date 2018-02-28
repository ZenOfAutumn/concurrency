package com.autumn.zen.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * Sequence DeadLock
 **/
public class SequenceDeadLock {

  private final Object left = new Object();

  private final Object right = new Object();

  public static void main(String[] args) {
    SequenceDeadLock deadLock = new SequenceDeadLock();

    Thread lr = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          deadLock.leftRight();
        } catch (InterruptedException e) {

        }
      }
    });

    Thread rl = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          deadLock.rightLeft();
        } catch (InterruptedException e) {

        }
      }
    });

    lr.start();
    rl.start();

  }

  public void leftRight() throws InterruptedException {
    synchronized (left) {
      TimeUnit.SECONDS.sleep(2);
      synchronized (right) {
        TimeUnit.SECONDS.sleep(2);
      }
    }
  }

  public void rightLeft() throws InterruptedException {
    synchronized (right) {
      TimeUnit.SECONDS.sleep(2);
      synchronized (left) {
        TimeUnit.SECONDS.sleep(2);
      }
    }
  }
}
