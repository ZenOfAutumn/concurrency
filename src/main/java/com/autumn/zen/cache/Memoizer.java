package com.autumn.zen.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 *
 *
 **/

public class Memoizer<A, V> implements Computable<A, V> {

  private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();

  private final Computable<A, V> c;

  public Memoizer(Computable<A, V> c) {
    this.c = c;
  }

  @Override
  public V compute(final A a) {

    while (true) {
      Future<V> f = cache.get(a);
      if (f == null) {
        Callable<V> eval = new Callable<V>() {
          @Override
          public V call() throws Exception {
            return c.compute(a);
          }
        };
        FutureTask<V> ft = new FutureTask<V>(eval);
        f = cache.putIfAbsent(a, ft);
        if (f == null) {
          f = ft;
          ft.run();
        }
      }

      try {
        return f.get();
      } catch (CancellationException | InterruptedException e) {
        cache.remove(a, f);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }

    }
  }

}
