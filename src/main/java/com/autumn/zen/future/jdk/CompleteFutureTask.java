package com.autumn.zen.future.jdk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompleteFutureTask {

  public static void main(String[] args) {

    long timeOut = 600;
    Callable<String> complete = () -> {
      try {
        Thread.sleep(timeOut);
        return "Complete";
      } catch (InterruptedException e) {
        throw e;
      }
    };

    ExecutorService exe = Executors.newFixedThreadPool(1);
    Future<String> future = exe.submit(complete);

    try {
      System.out.println(future.get(600,TimeUnit.MILLISECONDS));
    } catch (TimeoutException | InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

  }

}
