package com.autumn.zen.object.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicObject {

  private static final int CHANNEL_CAPACITY = 10;

  private final AtomicInteger count = new AtomicInteger(0);

  private final AtomicIntegerArray countArray = new AtomicIntegerArray(CHANNEL_CAPACITY);

  private final AtomicReference<State> state = new AtomicReference<>(State.INIT);

  public static void main(String[] args) throws InterruptedException {

    AtomicObject object = new AtomicObject();

    Thread st = new Thread(() -> object.start());

    Thread state = new Thread(() -> {
      while (true) {
        try {
          System.out.println(object.getState());
          Thread.sleep(3000);
        } catch (InterruptedException ex) {

        }
      }
    });

    state.start();
    st.start();
    object.cancel();

    Thread.sleep(6000);

  }

  public void incret(int index, int delta) {
    if (index <= CHANNEL_CAPACITY) {
      countArray.addAndGet(index, delta);
    }
    throw new IllegalArgumentException("index exceed limit");
  }

  public Integer getValueByIndex(int index) {
    return countArray.get(index);
  }

  public boolean start() {
    return this.state.compareAndSet(State.INIT, State.RUNNABLE);
  }

  public boolean cancel() {
    return this.state.compareAndSet(State.RUNNABLE, State.END);
  }

  public State getState() {
    return this.state.get();
  }

  enum State {
    INIT,
    RUNNABLE,
    END
  }

}
