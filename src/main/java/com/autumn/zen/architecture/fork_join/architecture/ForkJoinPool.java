package com.autumn.zen.architecture.fork_join.architecture;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 **/
public class ForkJoinPool extends AbstractExecutorService {

  final void runWorker(WorkQueue w) {

  }

  final void deregisterWorker(ForkJoinWorkerThread workerThread, Throwable ex) {

  }

  final WorkQueue registerWorker(ForkJoinWorkerThread wt) {
    WorkQueue retWorkQueue = new WorkQueue(this,wt);
    return retWorkQueue;
  }

  @Override
  public void shutdown() {

  }

  @Override
  public List<Runnable> shutdownNow() {
    return null;
  }

  @Override
  public boolean isShutdown() {
    return false;
  }

  @Override
  public boolean isTerminated() {
    return false;
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return false;
  }

  @Override
  public void execute(Runnable command) {

  }

  static final class WorkQueue {

    // 工作密取队列初始容量
    static final int INITIAL_QUEUE_CAPACITY = 1 << 13;

    // 工作密取队列最大容量
    static final int MAXIMUM_QUEUE_CAPACITY = 1 << 26;

    final ForkJoinPool pool;   // the containing pool (may be null)

    final ForkJoinWorkerThread owner; // owning thread or null if shared

    volatile int scanState;    // versioned, <0: inactive; odd:scanning

    volatile int base; // index of next slot for poll

    int top; // index of next slot for push

    short poolIndex; // index of this queue in pool

    WorkQueue(ForkJoinPool pool, ForkJoinWorkerThread owner) {
      this.pool = pool;
      this.owner = owner;
      base = top = INITIAL_QUEUE_CAPACITY >>> 1;
    }

  }

  public static void main(String[] args) {
    System.out.println(-16>>1);
    System.out.println(-16>>>1);

  }
}
