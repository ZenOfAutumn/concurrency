package com.autumn.zen.architecture.fork_join.architecture;

/**
 *
 *
 **/
public class ForkJoinWorkerThread extends Thread {

  final ForkJoinPool pool;

  final ForkJoinPool.WorkQueue workQueue;

  protected ForkJoinWorkerThread(ForkJoinPool pool) {
    super("ForkJoinWorkerThread");
    this.pool = pool;
    this.workQueue = pool.registerWorker(this);
  }

  public ForkJoinPool getPool() {
    return pool;
  }

  /**
   * 返回该线程在线程池中的唯一索引
   */
  public int getPoolIndex() {
    return workQueue.poolIndex >>> 1;
  }

  /**
   * 在处理任何任务之前的初始化操作
   */
  protected void onStart() {

  }

  /**
   * 线程终结时执行清理操作
   */
  protected void onTermination(Throwable exception) {
  }

  /**
   * 线程主循环，禁止显式调用
   */
  @Override
  public void run() {
    Throwable exception = null;
    try {
      onStart();
      pool.runWorker(workQueue);
    } catch (Throwable ex) {
      exception = ex;
    } finally {
      try {
        onTermination(exception);
      } catch (Throwable ex) {
        if (exception == null) {
          exception = ex;
        }
      } finally {
        pool.deregisterWorker(this, exception);
      }
    }
  }

}
