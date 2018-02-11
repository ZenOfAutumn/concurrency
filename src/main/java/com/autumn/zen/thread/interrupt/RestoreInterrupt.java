package com.autumn.zen.thread.interrupt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RestoreInterrupt {

  public static void main(String[] args) throws InterruptedException {
    RunnableTaskWorker worker = new RunnableTaskWorker(new LinkedBlockingQueue<>());
    Thread t = new Thread(worker);
    t.start();
    t.interrupt();
    System.out.println("after restore: " + t.isInterrupted());
    worker.setExit(true);
  }

  static class RunnableTaskWorker implements Runnable {

    BlockingQueue<String> queue;

    volatile boolean exit = false;

    public RunnableTaskWorker(BlockingQueue<String> queue) {
      this.queue = queue;
    }

    public void setExit(boolean exit) {
      this.exit = exit;
    }

    @Override
    public void run() {

      try {
        queue.take();
      } catch (InterruptedException e) {
        System.out.println("before restore: " + Thread.currentThread().isInterrupted());
        Thread.currentThread().interrupt();
        while (!exit) {

        }
      }
    }
  }

}
