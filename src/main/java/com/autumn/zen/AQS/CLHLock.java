package com.autumn.zen.AQS;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import sun.misc.Unsafe;

/**
 * CLH Lock
 **/
public class CLHLock {

  public static final long valueOffset;

  private static final Unsafe unsafe;

  private static final ThreadLocal<CLHNode> localNode;

  static {

    try {
      unsafe = getUnsafeInstance();
      valueOffset = unsafe.objectFieldOffset(CLHLock.class.getDeclaredField("tail"));
      localNode = new ThreadLocal<>();
    } catch (Exception e) {
      throw new Error(e);
    }
  }

  private volatile CLHNode tail;

  private static Unsafe getUnsafeInstance() throws NoSuchFieldException, IllegalAccessException {
    Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
    theUnsafeInstance.setAccessible(true);
    return (Unsafe) theUnsafeInstance.get(Unsafe.class);
  }

  public static void main(String[] args) throws InterruptedException {
    CLHLock lock = new CLHLock();
    Runnable task = new Runnable() {
      @Override
      public void run() {
        lock.lock();
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }
    };

    int num = Runtime.getRuntime().availableProcessors() + 1;
    Thread[] threads = new Thread[num];
    for (int i = 0; i < num; i++) {
      Thread t = new Thread(task);
      threads[i] = t;
      t.start();
    }

  }

  private void lock(CLHNode currentThreadNode) {
    CLHNode prev = null;
    for (; ; ) {
      prev = tail;
      if (unsafe.compareAndSwapObject(this, valueOffset, tail, currentThreadNode)) {
        break;
      }
    }

    if (prev != null) {
      while (prev.isLocked) {
        // self spin
      }
    }
    System.out.println(Thread.currentThread().getName() + " get the lock");
  }

  private void unlock(CLHNode currentThreadNode) {
    if (!unsafe.compareAndSwapObject(this, valueOffset, currentThreadNode, null)) {
      currentThreadNode.isLocked = false;
    }
    System.out.println(Thread.currentThread().getName() + " release the lock");

  }

  public void lock() {
    CLHNode currentThreadNode = new CLHNode();
    localNode.set(currentThreadNode);
    lock(currentThreadNode);
  }

  public void unlock() {
    CLHNode currentThreadNode = localNode.get();
    if (currentThreadNode != null) {
      unlock(currentThreadNode);
    }
  }

  static class CLHNode {

    private volatile boolean isLocked = true;
  }

}
