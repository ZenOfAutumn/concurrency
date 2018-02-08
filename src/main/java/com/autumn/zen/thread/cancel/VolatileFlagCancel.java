package com.autumn.zen.thread.cancel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VolatileFlagCancel implements Runnable {

  private final List<BigInteger> primes = new ArrayList<>();

  private volatile boolean cancelled;

  public static List<BigInteger> primes(int seconds) {
    VolatileFlagCancel cancel = new VolatileFlagCancel();
    new Thread(cancel).start();
    try {
      TimeUnit.SECONDS.sleep(seconds);
    } catch (InterruptedException e) {

    } finally {

      cancel.cancel();
    }
    return cancel.get();
  }

  public static void main(String[] args) {
    System.out.println(primes(1));
  }

  @Override
  public void run() {

    BigInteger p = BigInteger.ONE;
    while (!cancelled) {
      p = p.nextProbablePrime();
      synchronized (this) {
        primes.add(p);
      }
    }

  }

  public void cancel() {
    cancelled = true;
  }

  public synchronized List<BigInteger> get() {
    return new ArrayList<BigInteger>(primes);
  }
}
