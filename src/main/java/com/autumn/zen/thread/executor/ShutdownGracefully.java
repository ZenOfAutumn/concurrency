package com.autumn.zen.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Shutdown ExecutorService Gracefully
 **/
public class ShutdownGracefully {

  public static void main(String[] args) {

    ExecutorService service = Executors.newSingleThreadExecutor();

    try {
      try {
        service.execute(new ShortTask());
        service.execute(new LongTask());
      } finally {
        service.shutdown();
        System.out.println("service shutdown: " + service.isShutdown());
        while (!service.awaitTermination(1, TimeUnit.SECONDS)) {
          System.out.println("service terminate: " + service.isTerminated());
        }

        System.out.println("service terminate: " + service.isTerminated());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static class ShortTask implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static class LongTask implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
