package com.autumn.zen.pattern.disruptor.app;

import com.autumn.zen.pattern.disruptor.app.MultiProducerSingleConsumer.DataPublishProducer;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleProducerMultiLevelConsumer {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();
    DataFactory factory = new DataFactory();
    int bufferSize = 1024;

    Disruptor<Data> disruptor = new Disruptor<Data>(factory, bufferSize, executor,
      ProducerType.SINGLE, new BlockingWaitStrategy());

    WorkHandler[] plusOne = new WorkHandler[4];
    WorkHandler[] plusTwo = new WorkHandler[4];
    EventHandler[] multiTwo = new EventHandler[4];

    for (int i = 0; i < 4; i++) {
      plusOne[i] = new PlusOneConsumer();
      plusTwo[i] = new PlusTwoConsumer();
      multiTwo[i] = new Multi2Consumer();
    }

    EventHandlerGroup plusOneGroup = disruptor.handleEventsWithWorkerPool(plusOne);
    EventHandlerGroup plusTwoGroup = disruptor.handleEventsWithWorkerPool(plusTwo);
    plusOneGroup.and(plusTwoGroup).then(multiTwo).then(new SysOutConsumer());
    disruptor.start();

    Thread producer = new Thread(new DataPublishProducer(disruptor));
    producer.start();

  }

  static class PlusOneConsumer implements WorkHandler<Data> {

    @Override
    public void onEvent(Data event) throws Exception {
      System.out.println("Plus one get value: " + event.getValue());
      event.setValue(event.getValue() + 1);

    }
  }

  static class PlusTwoConsumer implements WorkHandler<Data> {

    @Override
    public void onEvent(Data event) throws Exception {
      System.out.println("Plus two get value: " + event.getValue());
      event.setValue(event.getValue() + 2);

    }
  }

  static class Multi2Consumer implements EventHandler<Data> {

    @Override
    public void onEvent(Data event, long sequence, boolean endOfBatch) throws Exception {
      System.out.println("Multi two get value: " + event.getValue());
      event.setValue(event.getValue() * 2);

    }
  }

  static class SysOutConsumer implements EventHandler<Data> {

    @Override
    public void onEvent(Data event, long sequence, boolean endOfBatch) throws Exception {

      System.out.println(event.getValue());
    }
  }

}
