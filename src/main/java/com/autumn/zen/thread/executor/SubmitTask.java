package com.autumn.zen.thread.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Submit Task with ExecutorService
 **/
public class SubmitTask implements Callable<String> {

  public static void main(String[] args) {

    ExecutorService service = Executors.newSingleThreadExecutor();
    Future<String> future = service.submit(new SubmitTask());

    try {
      String ret = future.get();
      System.out.println("future value: " + ret);
    } catch (InterruptedException e) {
      // reset interrupt status
      Thread.currentThread().interrupt();
      // cancel
      future.cancel(true);
    } catch (ExecutionException e) {
      e.printStackTrace();
      System.exit(1);
    } finally {
      service.shutdown();
    }

  }

  @Override
  public String call() throws Exception {
    Thread.sleep(1000);
    return Thread.currentThread().getName();
  }
}
