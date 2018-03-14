package com.autumn.zen.pattern.disruptor.app;

import com.autumn.zen.pattern.disruptor.translator.DataEventTranslator;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiProducerSingleConsumer {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();
    DataFactory factory = new DataFactory();
    int bufferSize = 1024;

    Disruptor<Data> disruptor = new Disruptor<Data>(factory, bufferSize, executor,
      ProducerType.MULTI, new BusySpinWaitStrategy());

    WorkHandler handler = new SingleConsumer();

    disruptor.handleEventsWithWorkerPool(handler);

    disruptor.start();

    RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();

    ExecutorService producerExector = Executors.newCachedThreadPool();

    for (int i = 0; i < 4; i++) {
      producerExector.execute(new DataPublishProducer(disruptor));
    }

  }

  public static class DataPublishProducer implements Runnable {

    private Disruptor disruptor;

    DataPublishProducer(Disruptor disruptor) {
      this.disruptor = disruptor;
    }

    @Override
    public void run() {
      for (int i = 0; i < 1; i++) {
        disruptor.publishEvent(new DataEventTranslator(i));
      }
    }
  }
}
