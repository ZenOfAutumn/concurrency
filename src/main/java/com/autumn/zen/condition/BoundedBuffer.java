package com.autumn.zen.condition;

/**
 * Bounded Buffer
 *
 * implement with object.wait()/object.notifyAll()
 **/
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

  public BoundedBuffer(int capacity) {
    super(capacity);
  }

  public synchronized V take() throws InterruptedException {
    while (isEmpty()) {
      wait();
    }
    V ret = doTake();
    notifyAll();
    return ret;
  }

  public synchronized void put(V value) throws InterruptedException {
    while (isFull()) {
      wait();
    }
    doPut(value);
    notifyAll();

  }

}
