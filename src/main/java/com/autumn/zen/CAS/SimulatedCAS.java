package com.autumn.zen.CAS;

public class SimulatedCAS {

  private int value;

  public synchronized int get() {
    return value;
  }

  public synchronized int compareAndSwap(int expectedValue, int newValue) {
    int oldValue = value;
    if (oldValue == expectedValue) {
      value = newValue;
    }
    return oldValue;
  }

  public synchronized boolean compareAndSet(int expectedValue, int newValue) {
    return expectedValue == compareAndSwap(expectedValue, newValue);
  }

}
