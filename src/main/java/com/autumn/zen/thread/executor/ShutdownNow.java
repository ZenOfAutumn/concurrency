package com.autumn.zen.thread.executor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Shutdown ExecutorService Now
 **/
public class ShutdownNow {

  public static void main(String[] args) {

    ExecutorService service = Executors.newSingleThreadExecutor();

    try {
      service.execute(new ShortTask());
      service.execute(new LongTask());
      service.execute(new LongTask());
    } finally {
      List<Runnable> unExecuteTask = service.shutdownNow();
      System.out.println("executor service terminate: " + service.isTerminated());
      for (Runnable runnable : unExecuteTask) {
        if (Namable.class.isAssignableFrom(runnable.getClass())) {
          Namable task = (Namable) runnable;
          System.out.println("unExecuteTask: " + task.name());
        }
      }
    }

  }

  public interface Namable {

    String name();
  }

  public static class ShortTask implements Runnable, Namable {

    @Override
    public String name() {
      return "shortTask";
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {

      }
    }
  }

  public static class LongTask implements Runnable, Namable {

    @Override
    public String name() {
      return "longTask";
    }

    @Override
    public void run() {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {

      }
    }
  }
}
