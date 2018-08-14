package com.autumn.zen.future.simple;

public class RealData<T> implements Data<T> {

  private T result;

  public RealData(T t) throws InterruptedException {
    Thread.sleep(6000);
    this.result = t;
  }

  public T getResult() {
    return result;
  }
}
