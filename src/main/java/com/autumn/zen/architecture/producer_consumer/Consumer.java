package com.autumn.zen.architecture.producer_consumer;

import java.util.concurrent.BlockingQueue;

public abstract class Consumer<T> {

  private BlockingQueue<T> queue;

  public Consumer(BlockingQueue<T> queue) {
    this.queue = queue;
  }

  public void consume() throws InterruptedException {
    T item = queue.take();
    consumeInternal(item);
  }

  protected abstract void consumeInternal(T item);

}
