package com.autumn.zen.tool;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;

public class ExchangerEx {

  public static void main(String[] args) {

    Exchanger<List<Integer>> xc = new Exchanger<>();

    List<Integer> producerHolder = new CopyOnWriteArrayList<>();
    List<Integer> consumerHolder = new CopyOnWriteArrayList<>();
    Generator<Integer> generator = new Generator<Integer>() {
      @Override
      public Integer next() {
        return new Random().nextInt(10);
      }
    };

    new Thread(new ExchangerProducer<>(xc, producerHolder, generator)).start();
    new Thread(new ExchangerConsumer<>(xc, consumerHolder)).start();

  }

  interface Generator<T> {

    T next();
  }

  static class ExchangerConsumer<T> implements Runnable {

    private Exchanger<List<T>> exchanger;

    private List<T> holder;

    private volatile T value;

    ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder) {
      this.exchanger = exchanger;
      this.holder = holder;
    }

    public static <T> BasicGenerator<T> create(Class<T> type) {
      return new BasicGenerator<>(type);
    }

    @Override
    public void run() {

      try {
        int time = 1;
        while (!Thread.interrupted()) {
          holder = exchanger.exchange(holder);
          for (T t : holder) {
            value = t;
            holder.remove(t);
            System.out.println(t);
          }
          System.out.println("exchanger time is: " + time);
          time++;
        }
      } catch (InterruptedException e) {
      }
    }
  }

  static class BasicGenerator<T> implements Generator {

    private Class<T> type;

    BasicGenerator(Class<T> type) {
      this.type = type;
    }

    @Override
    public T next() {

      try {
        return type.newInstance();
      } catch (IllegalAccessException | InstantiationException e) {
        throw new RuntimeException(e);
      }
    }
  }

  static class ExchangerProducer<T> implements Runnable {

    private Generator<T> generator;

    private Exchanger<List<T>> exchanger;

    private List<T> holder;

    ExchangerProducer(Exchanger<List<T>> exchanger, List<T> holder, Generator<T> generator) {
      this.exchanger = exchanger;
      this.holder = holder;
      this.generator = generator;
    }

    @Override
    public void run() {
      try {

        while (!Thread.interrupted()) {
          for (int i = 0; i < 6; i++) {
            holder.add(generator.next());
          }
          holder = exchanger.exchange(holder);
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);

      }

    }
  }
}
