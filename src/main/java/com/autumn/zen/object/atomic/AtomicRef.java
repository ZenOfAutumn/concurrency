package com.autumn.zen.object.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRef {

  public static void main(String[] args) {

    AtomicReference<Node> atomicNode = new AtomicReference<>();
    System.out.println(atomicNode.compareAndSet(null, Node.EMPTY));
    System.out.println(atomicNode.compareAndSet(Node.EMPTY, Node.NULL));
    System.out.println(atomicNode.compareAndSet(Node.EMPTY, null));
    System.out.println(Node.NULL == atomicNode.get());


  }

  public static class Node {

    public static final Node EMPTY = new Node();

    public static final Node NULL = null;

  }

}
