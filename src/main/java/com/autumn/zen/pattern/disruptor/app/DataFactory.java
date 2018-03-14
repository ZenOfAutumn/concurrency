package com.autumn.zen.pattern.disruptor.app;

import com.lmax.disruptor.EventFactory;

public class DataFactory implements EventFactory<Data> {

  public Data newInstance() {
    return new Data();
  }
}
