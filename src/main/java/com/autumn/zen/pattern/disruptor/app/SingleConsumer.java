package com.autumn.zen.pattern.disruptor.app;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.WorkHandler;

public class SingleConsumer implements WorkHandler<Data>, LifecycleAware {

  @Override
  public void onEvent(Data event) throws Exception {
    System.out.println(event.getThreadName() + " ：" + event.getValue());
  }

  @Override
  public void onStart() {
    System.out.println("Single consumer start.");

  }

  @Override
  public void onShutdown() {
    System.out.println("Single consumer shutdown.");
  }
}
