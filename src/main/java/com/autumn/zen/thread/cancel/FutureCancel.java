package com.autumn.zen.thread.cancel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Cancel Task by Future
 **/
public class FutureCancel {

  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    Future<String> future = executorService.submit(new Job());

    try {
      future.get(300, TimeUnit.MILLISECONDS);
    } catch (TimeoutException e) {
      // wait cancel
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    } finally {
      future.cancel(true);
    }

  }

  static class Job implements Callable<String> {

    @Override
    public String call() throws Exception {
      Thread.sleep(2000);
      // some work

      return "Job";
    }
  }

}
