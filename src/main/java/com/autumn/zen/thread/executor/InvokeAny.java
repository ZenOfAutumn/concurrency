package com.autumn.zen.thread.executor;

import com.autumn.zen.thread.executor.InvokeAll.RpcCall;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * InvokeAll
 **/
public class InvokeAny {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    Collection<RpcCall> rpcCalls = Lists.newArrayList();
    for (int i = 0; i < 100; i++) {
      rpcCalls.add(new RpcCall());
    }

    try {
      Double rpcResponse = executorService.invokeAny(rpcCalls, 4, TimeUnit.SECONDS);
      System.out.println("ret value: " + rpcResponse);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }

  }


}
