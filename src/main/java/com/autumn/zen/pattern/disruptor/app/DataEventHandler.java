package com.autumn.zen.pattern.disruptor.app;

import com.lmax.disruptor.EventHandler;

public class DataEventHandler implements EventHandler<Data> {


  @Override
  public void onEvent(Data event, long sequence, boolean endOfBatch) throws Exception {
    System.out.println(
      Thread.currentThread().getName() + " value: " + event.getValue() + " sequence: " + sequence);

  }
}
