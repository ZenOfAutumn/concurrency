package com.autumn.zen.architecture.producer_consumer;

import java.util.concurrent.BlockingQueue;

/**
 *
 *
 **/
public class ItemProducer extends Producer<Item> {

  public ItemProducer(BlockingQueue<Item> queue) {
    super(queue);
  }

  @Override
  protected Item produceInternal() {
    Item item = new Item();
    item.setName(Thread.currentThread().getName());
    return item;
  }
}
