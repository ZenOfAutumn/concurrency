package com.autumn.zen.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bounded Buffer with Condition
 **/
public class ConditionBoundedBuffer<V> {

  protected final ReentrantLock lock = new ReentrantLock();

  private final Condition notFull = lock.newCondition();

  private final Condition notEmpty = lock.newCondition();

  private final V[] buffer;

  private int tail;

  private int head;

  private int count;

  public ConditionBoundedBuffer(int capacity) {
    this.buffer = (V[]) new Object[capacity];
  }

  public V take() throws InterruptedException {
    try {
      lock.lock();
      while (count == 0) {
        notEmpty.await();
      }
      V ret = buffer[head];
      if (++head == buffer.length) {
        head = 0;
      }
      count--;
      notFull.signal();
      return ret;
    } finally {
      lock.unlock();
    }
  }

  public void put(V value) throws InterruptedException {
    try {
      lock.lock();
      while (count == buffer.length) {
        notFull.await();
      }
      buffer[tail] = value;
      if (++tail == buffer.length) {
        tail = 0;
      }
      count++;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

}
