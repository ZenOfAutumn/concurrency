package com.autumn.zen.thread.pool;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 **/
public class TimingThreadPool extends ThreadPoolExecutor {

  private final ThreadLocal<Long> startTime = new ThreadLocal<>();

  private final AtomicLong numTasks = new AtomicLong();

  private final AtomicLong totalTime = new AtomicLong();

  public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public static void main(String[] args) {

    ExecutorService service = new TimingThreadPool(1, 1,
      0L, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<Runnable>());

    for (int i = 0; i < 10; i++) {
      service.execute(new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(new Random().nextInt(100));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }

    service.shutdown();

  }

  @Override
  protected void beforeExecute(Thread t, Runnable r) {
    super.beforeExecute(t, r);
    System.out.println("Thread: " + t.getName() + " start: " + r);
    startTime.set(System.nanoTime());
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {

    try {
      long endTime = System.nanoTime();
      long taskTime = endTime - startTime.get();
      numTasks.incrementAndGet();
      totalTime.addAndGet(taskTime);
      System.out.println("task run time: " + taskTime);
    } finally {
      super.afterExecute(r, t);
    }
  }

  @Override
  protected void terminated() {

    try {
      System.out.println("avg time: " + totalTime.get() / numTasks.get());
    } finally {
      super.terminated();

    }
  }
}
