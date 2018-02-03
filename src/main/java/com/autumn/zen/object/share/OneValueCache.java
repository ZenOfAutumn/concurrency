package com.autumn.zen.object.share;

import java.math.BigInteger;
import java.util.Arrays;

public class OneValueCache {

  private final BigInteger lastNumber;

  private final BigInteger[] lastFactors;

  public OneValueCache(BigInteger i, BigInteger[] factors) {
    this.lastNumber = i;
    this.lastFactors = Arrays.copyOf(factors, factors.length);
  }

  public static void main(String[] args) {
    VolatileCacheHolder<OneValueCache> cacheHolder = new VolatileCacheHolder<>();
  }

  public BigInteger[] getFactors(BigInteger i) {
    if (lastNumber == null || !lastNumber.equals(i)) {
      return null;
    }
    return lastFactors;
  }
}

class VolatileCacheHolder<T> {

  private volatile T cache;

  public T getCache() {
    return cache;
  }

  public void setCache(T cache) {
    this.cache = cache;
  }
}
