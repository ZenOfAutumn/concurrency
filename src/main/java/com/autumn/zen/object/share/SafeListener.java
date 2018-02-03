package com.autumn.zen.object.share;

public class SafeListener {

  private final EventListener listener;

  private SafeListener() {
    listener = new EventListener() {
      @Override
      public Object accept() {
        return SafeListener.this;
      }
    };
  }

  public static SafeListener newInstance(EventSource source) {
    SafeListener safeListener = new SafeListener();
    source.register(safeListener.getListener());
    return safeListener;
  }

  public EventListener getListener() {
    return listener;
  }

}
