package com.autumn.zen.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockEx implements Runnable {

  private ReentrantLock lock;

  @Override
  public void run() {
    try {
      lock.tryLock();

      // do something
    } finally {
      lock.unlock();
    }
  }



}
