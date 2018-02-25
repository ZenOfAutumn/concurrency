package com.autumn.zen.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 *
 **/
public class ExceptionHandledThreadFactory implements ThreadFactory {

  public static void main(String[] args) {

    ExecutorService exe = Executors.newSingleThreadExecutor(new ExceptionHandledThreadFactory());

    exe.execute(new Runnable() {
      @Override
      public void run() {
        throw new RuntimeException();
      }
    });

    exe.shutdown();
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread thread = new Thread(r);
    thread.setUncaughtExceptionHandler(new SystemLogExceptionHandler());
    return thread;
  }
}
