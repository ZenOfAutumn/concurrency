package com.autumn.zen.future.simple;

public class Client {

  public <T> FutureData<T> call(T t) {
    FutureData futureData = new FutureData();
    new Thread(() -> {
      try {
        RealData realData = new RealData(t);
        futureData.setRealData(realData);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    ).start();

    return futureData;
  }

}
