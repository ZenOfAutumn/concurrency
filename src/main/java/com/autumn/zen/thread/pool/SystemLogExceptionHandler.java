package com.autumn.zen.thread.pool;

import java.lang.Thread.UncaughtExceptionHandler;

public class SystemLogExceptionHandler implements UncaughtExceptionHandler {

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    System.out.println(t.getName() + " throw exception: " + e.getClass().getCanonicalName());
  }
}
