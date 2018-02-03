package com.autumn.zen.object.share;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * escape this reference by inner class
 **/

interface EventListener {

  Object accept();
}

class EventSource {

  private List<EventListener> listeners;

  public void register(EventListener listener) {
    if (listeners == null) {
      listeners = new ArrayList<EventListener>();
    }

    listeners.add(listener);
  }

  public List<EventListener> getListeners() {
    return listeners;
  }
}

public class EscapeByInnerClass {

  private static final Logger LOG = LoggerFactory.getLogger(EscapeByInnerClass.class);

  private EventSource eventSource;

  public EscapeByInnerClass(EventSource eventSource) throws InterruptedException {
    eventSource.register(new EventListener() {
      @Override
      public Object accept() {
        return EscapeByInnerClass.this;
      }
    });
    Thread.sleep(1000);
    this.eventSource = eventSource;
  }

  public static void main(String[] args) {

    final EventSource eventSource = new EventSource();

    Thread init = new Thread() {
      @Override
      public void run() {
        try {
          new EscapeByInnerClass(eventSource);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    init.start();

    while (true) {
      if (eventSource.getListeners() != null && eventSource.getListeners().size() != 0) {
        EventListener listener = eventSource.getListeners().get(0);
        if (listener.accept() != null) {
          EscapeByInnerClass escape = (EscapeByInnerClass) listener.accept();
          if (escape.getEventSource() == null) {
            LOG.info("initial finished: {}", false);
          } else {
            LOG.info("initial finished: {}", true);
            break;
          }
        }

      }
    }
  }

  public EventSource getEventSource() {
    return eventSource;
  }

}
