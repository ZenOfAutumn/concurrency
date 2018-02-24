package com.autumn.zen.thread.executor;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * InvokeAll
 **/
public class InvokeAll {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    Collection<RpcCall> rpcCalls = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      rpcCalls.add(new RpcCall());
    }

    try {
      List<Future<Double>> rpcResponses = executorService.invokeAll(rpcCalls, 4, TimeUnit.SECONDS);
      for (Future<Double> rpcResponse : rpcResponses) {
        try {
          Double ret = rpcResponse.get();
          System.out.println("ret done value: " + ret);
        } catch (ExecutionException e) {
          System.out.println("ret failed value");
        } catch (CancellationException e) {
          System.out.println("ret cancel value");
        }
      }

    } catch (InterruptedException e) {

    } finally {
      executorService.shutdown();
    }

  }

  public static class RpcCall implements Callable<Double> {

    @Override
    public Double call() throws Exception {
      // mock rpc call
      Random random = new Random();
      Thread.sleep(random.nextInt(1000));
      return random.nextDouble();
    }
  }

}
