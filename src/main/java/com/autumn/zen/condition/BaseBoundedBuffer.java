package com.autumn.zen.condition;

/**
 * Base Bounded Buffer
 *
 * implemented with synchronized
 *
 **/
public class BaseBoundedBuffer<V> {

  private final V[] buffer;

  private int tail;

  private int head;

  private int count;

  public BaseBoundedBuffer(int capacity) {
    this.buffer = (V[]) new Object[capacity];
  }

  public synchronized boolean isEmpty() {
    return count == 0;
  }

  public synchronized boolean isFull() {
    return count == buffer.length;
  }

  protected synchronized void doPut(V value) {
    buffer[tail] = value;
    if (++tail == buffer.length) {
      tail = 0;
    }
    count++;
  }

  protected synchronized V doTake() {
    V ret = buffer[head];
    buffer[head] = null;
    if (++head == buffer.length) {
      head = 0;
    }
    count--;
    return ret;
  }


}
