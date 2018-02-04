package com.autumn.zen.object.extend;

import java.util.Vector;

public class ExtendVector<E> extends Vector<E> {

  public synchronized boolean putIfAbsent(E x) {
    boolean absent = !contains(x);
    if (absent) {
      add(x);
    }
    return absent;
  }
}
