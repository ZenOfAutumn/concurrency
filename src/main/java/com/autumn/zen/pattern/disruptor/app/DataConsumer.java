package com.autumn.zen.pattern.disruptor.app;

import com.lmax.disruptor.WorkHandler;

public class DataConsumer implements WorkHandler<Data> {

  public void onEvent(Data data) throws Exception {
    System.out.println(Thread.currentThread().getName() + " value: " + data.getValue());
  }
}
