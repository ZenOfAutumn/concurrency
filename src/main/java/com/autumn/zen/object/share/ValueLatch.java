package com.autumn.zen.object.share;

import java.util.concurrent.CountDownLatch;

/**
 *
 *
 **/
public class ValueLatch<V> {

  private final CountDownLatch latch = new CountDownLatch(1);

  private V value = null;

  public boolean isSet() {
    return (latch.getCount() == 0);
  }

  public V getValue() throws InterruptedException {
    latch.await();
    synchronized (this) {
      return value;
    }

  }

  public synchronized void setValue(V value) {
    if (!isSet()) {
      this.value = value;
      latch.countDown();
    }
  }

}
