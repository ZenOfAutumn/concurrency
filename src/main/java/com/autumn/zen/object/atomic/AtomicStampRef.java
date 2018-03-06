package com.autumn.zen.object.atomic;

import com.autumn.zen.object.atomic.AtomicRef.Node;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampRef {

  public static void main(String[] args) {

    AtomicStampedReference<Node> stampedReference = new AtomicStampedReference<Node>(null,
      1);
    System.out.println(stampedReference.compareAndSet(null, Node.EMPTY, 1, 2));
    stampedReference.attemptStamp(Node.EMPTY, 3);
    System.out.println(stampedReference.getReference() == Node.EMPTY);
    System.out.println(stampedReference.getStamp());

  }

}
