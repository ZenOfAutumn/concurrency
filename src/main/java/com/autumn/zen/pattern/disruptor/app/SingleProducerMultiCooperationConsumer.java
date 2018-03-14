package com.autumn.zen.pattern.disruptor.app;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleProducerMultiCooperationConsumer {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();
    DataFactory factory = new DataFactory();
    int bufferSize = 1024;

    Disruptor<Data> disruptor = new Disruptor<Data>(factory, bufferSize, executor,
      ProducerType.SINGLE, new BlockingWaitStrategy());

    disruptor.handleEventsWithWorkerPool(new DataConsumer(), new DataConsumer(), new DataConsumer(),
      new DataConsumer());

    disruptor.start();

    RingBuffer<Data> ringBuffer = disruptor.getRingBuffer();
    DataProducer producer = new DataProducer(ringBuffer);
    ByteBuffer buffer = ByteBuffer.allocate(8);
    for (int i = 0; i < 1000; i++) {
      buffer.putLong(0, i);
      producer.pushData(buffer);
      Thread.sleep(100);

    }

  }
}
