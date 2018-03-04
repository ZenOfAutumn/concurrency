package com.autumn.zen.AQS;

import ch.qos.logback.core.net.SyslogOutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import sun.misc.Unsafe;

/**
 * Spin Lock
 **/
public class SpinLock {

  private static final long valueOffset;

  private static Unsafe unsafe = null;

  static {
    try {
      unsafe = getUnsafeInstance();
      valueOffset = unsafe.objectFieldOffset(SpinLock.class.getDeclaredField("value"));
    } catch (Exception e) {
      throw new Error(e);
    }
  }

  private volatile int value = 0;

  private static Unsafe getUnsafeInstance() throws NoSuchFieldException, IllegalAccessException {
    Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
    theUnsafeInstance.setAccessible(true);
    return (Unsafe) theUnsafeInstance.get(Unsafe.class);
  }

  public static void main(String[] args) {
    SpinLock lock = new SpinLock();
    Runnable task = new Runnable() {
      @Override
      public void run() {
        try {
          lock.lock();
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
        }
      }
    };

    Thread runThread = new Thread(task);
    runThread.start();
    Thread waitThread = new Thread(task);
    waitThread.start();
    System.out.println("run thread state: " + runThread.getState());
    System.out.println("waiting thread state: " + waitThread.getState());
  }

  public void lock() {
    for (; ; ) {
      int newValue = value + 1;
      if (newValue == 1) {
        if (unsafe.compareAndSwapInt(this, valueOffset, 0, newValue)) {
          return;
        }
      }
    }
  }

  public void unlock() {
    for (; ; ) {
      if (unsafe.compareAndSwapInt(this, valueOffset, 1, 0)) {
        return;
      }
    }
  }
}
