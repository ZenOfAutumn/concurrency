package com.autumn.zen.architecture.producer_consumer;

import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemConsumer extends Consumer<Item> {

  private static final Logger LOG = LoggerFactory.getLogger(ItemConsumer.class);

  public ItemConsumer(BlockingQueue<Item> queue) {
    super(queue);
  }

  @Override
  protected void consumeInternal(Item item) {
    LOG.info("{} consume item from {}", Thread.currentThread().getName(), item.getName());
  }
}
