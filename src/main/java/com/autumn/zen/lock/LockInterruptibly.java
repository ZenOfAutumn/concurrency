package com.autumn.zen.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock Interruptibly
 **/
public class LockInterruptibly implements Runnable {

  private ReentrantLock lock;

  public LockInterruptibly(ReentrantLock lock) {
    this.lock = lock;
  }

  public static void main(String[] args) {
    ReentrantLock lock = new ReentrantLock();
    Thread runThread = new Thread(new LockInterruptibly(lock));
    Thread waitThread = new Thread(new LockInterruptibly(lock));
    runThread.start();
    waitThread.start();
    waitThread.interrupt();

  }

  @Override
  public void run() {
    try {
      lock.lockInterruptibly();
      TimeUnit.SECONDS.sleep(10);
    } catch (InterruptedException e) {
      System.out.println(Thread.currentThread().getName() + " throw new InterruptedException");
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }
}
