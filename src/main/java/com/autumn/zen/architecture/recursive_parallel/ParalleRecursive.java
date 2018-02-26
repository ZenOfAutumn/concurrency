package com.autumn.zen.architecture.recursive_parallel;

import java.util.List;
import java.util.concurrent.Executor;

/**
 *
 *
 **/
public abstract class ParalleRecursive {

  private List[] process(List[] nextLevel) {
    return nextLevel;
  }

  public <V> void recursive(final Executor exec, V result, List... nextLevel) {
    for (List list : nextLevel) {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          // some work;
        }
      });
    }

    nextLevel = process(nextLevel); // ref
    recursive(exec, result, nextLevel);
  }

}
