package com.autumn.zen.object.atomic;

import com.autumn.zen.object.atomic.AtomicRef.Node;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkRef {

  public static void main(String[] args) {

    AtomicMarkableReference<Node> markableReference = new AtomicMarkableReference<Node>(null,
      false);
    System.out.println(markableReference.compareAndSet(null, Node.EMPTY, false, true));
    markableReference.attemptMark(Node.EMPTY, false);
    System.out.println(markableReference.getReference() == Node.EMPTY);
    System.out.println(markableReference.isMarked());

  }

}
