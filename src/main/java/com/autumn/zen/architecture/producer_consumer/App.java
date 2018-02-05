package com.autumn.zen.architecture.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 **/
public class App {

  private static final Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {

    BlockingQueue<Item> queue = new LinkedBlockingQueue<>(45);

    ExecutorService producers = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 5; i++) {
      producers.execute(() -> {
        try {
          Producer producer = new ItemProducer(queue);
          for (int j = 0; j < 10; j++) {
            producer.produce();
          }
        } catch (InterruptedException e) {
          Thread.interrupted();
        }
      });
    }

    ExecutorService consumers = Executors.newFixedThreadPool(5);
    for (int i = 0; i < 5; i++) {
      consumers.execute(() -> {
        try {
          Consumer consumer = new ItemConsumer(queue);
          while (true) {
            if (!Thread.currentThread().isInterrupted()) {
              consumer.consume();
            }
          }
        } catch (InterruptedException e) {
          Thread.interrupted();
        }
      });
    }

    try {
      for (int i = 10; i > 0; i--) {
        TimeUnit.SECONDS.sleep(1);
        LOG.info("queue size:{}", queue.size());
      }
      producers.shutdownNow();
      consumers.shutdownNow();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
}
