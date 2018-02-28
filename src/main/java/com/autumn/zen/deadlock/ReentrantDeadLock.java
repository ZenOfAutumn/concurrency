package com.autumn.zen.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sequence DeadLock
 **/
public class ReentrantDeadLock {

  private final Lock left = new ReentrantLock();

  private final Lock right = new ReentrantLock();

  public static void main(String[] args) {
    ReentrantDeadLock deadLock = new ReentrantDeadLock();

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
    try {
      left.lock();
      TimeUnit.SECONDS.sleep(2);
      try {
        right.lock();
        TimeUnit.SECONDS.sleep(2);
      } finally {
        right.unlock();
      }
    } finally {
      left.unlock();
    }
  }

  public void rightLeft() throws InterruptedException {
    try {
      right.lock();
      TimeUnit.SECONDS.sleep(2);
      try {
        left.lock();
        TimeUnit.SECONDS.sleep(2);
      } finally {
        left.unlock();
      }
    } finally {
      right.unlock();
    }
  }
}
