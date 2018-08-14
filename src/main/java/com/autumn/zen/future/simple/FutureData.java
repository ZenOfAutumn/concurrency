package com.autumn.zen.future.simple;

public class FutureData<T> implements Data<T> {

  private RealData<T> realData;

  private volatile boolean isReady;

  public synchronized void setRealData(RealData realData) {
    if (isReady) {
      return;
    }
    this.realData = realData;
    isReady = true;
    notifyAll();
  }

  public synchronized T getResult() {
    while (!isReady) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return realData.getResult();
  }
}
