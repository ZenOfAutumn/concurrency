package com.autumn.zen.CAS;

public class CASCounter {

  private SimulatedCAS cas;

  public int get() {
    return cas.get();
  }

  public int increment() {
    int v;
    do {
      v = get();
    } while (!cas.compareAndSet(v, v + 1));
    return v + 1;
  }

}
