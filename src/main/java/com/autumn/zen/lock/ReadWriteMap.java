package com.autumn.zen.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * Read Write Thread Safe Map with ReadWriteLock
 **/
public class ReadWriteMap<K, V> {

  private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  private final ReadLock readLock = readWriteLock.readLock();

  private final WriteLock writeLock = readWriteLock.writeLock();

  private final Map<K, V> map;

  public ReadWriteMap(int initCapacity) {
    this.map = new HashMap<>(initCapacity);
  }

  public V get(K key) {
    readLock.lock();
    try {
      return map.get(key);
    } finally {
      readLock.unlock();
    }
  }

  public void put(K key, V value) {
    writeLock.lock();
    try {
      map.put(key, value);
    } finally {
      writeLock.unlock();
    }
  }

}
