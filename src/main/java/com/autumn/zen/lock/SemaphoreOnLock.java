package com.autumn.zen.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Semaphore based on Lock
 **/
public class SemaphoreOnLock {

  private final Lock lock = new ReentrantLock();

  private final Condition permitsAvailable = lock.newCondition();

  private int permits;

  public SemaphoreOnLock(int initialPermits) {
    lock.lock();
    try {
      this.permits = initialPermits;
    } finally {
      lock.unlock();
    }
  }

  public void acquire() throws InterruptedException {
    lock.lock();
    try {
      if (permits <= 0) {
        permitsAvailable.await();
      }
      permits--;
    } finally {
      lock.unlock();
    }
  }

  public void release() {
    lock.lock();
    try {
      permits++;
      permitsAvailable.signal();
    } finally {
      lock.unlock();
    }
  }

}
