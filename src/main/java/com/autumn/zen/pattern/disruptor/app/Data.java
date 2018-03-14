package com.autumn.zen.pattern.disruptor.app;

public class Data {

  private long value;

  private String threadName;

  public long getValue() {
    return value;
  }

  public void setValue(long value) {
    this.value = value;
  }

  public String getThreadName() {
    return threadName;
  }

  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }
}
