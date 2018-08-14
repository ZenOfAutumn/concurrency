package com.autumn.zen.pattern.disruptor.translator;

import com.autumn.zen.pattern.disruptor.app.Data;
import com.lmax.disruptor.EventTranslator;

public class DataEventTranslator implements EventTranslator<Data> {

  private long value;

  public DataEventTranslator(long value) {
    this.value = value;
  }

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  @Override
  public void translateTo(Data event, long sequence) {
    event.setValue(value);
    event.setThreadName(Thread.currentThread().getName());
  }
}
