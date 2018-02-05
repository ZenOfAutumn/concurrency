package com.autumn.zen.architecture.producer_consumer;

import java.util.concurrent.BlockingQueue;

public abstract class Producer<T> {

  private BlockingQueue<T> queue;

  public Producer(BlockingQueue<T> queue) {
    this.queue = queue;
  }

  public void produce() throws InterruptedException {
    queue.put(produceInternal());
  }

  protected abstract T produceInternal();

}
