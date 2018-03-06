package com.autumn.zen.CAS;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentLinkQueue<E> {

  private final Node<E> dummy = new Node<>(null, null);

  private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);

  private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

  public boolean put(E item) {
    Node<E> newNode = new Node<E>(item, null);
    while (true) {
      Node<E> curTail = tail.get();
      Node<E> tailNext = curTail.next.get();
      if (curTail == tail.get()) {
        if (tailNext != null) {
          // 队列处于不稳定状态，tail指向的当前节点的next不为空，但tail仍然为旧引用
          tail.compareAndSet(curTail, tailNext);
        } else {
          // 队列处于稳定状态，tail指向的当前节点的next为空，直接入队
          if (curTail.next.compareAndSet(null, newNode)) {
            tail.compareAndSet(curTail, newNode);
            return true;
          }
        }
      }
    }
  }

  private static class Node<E> {

    final E item;

    final AtomicReference<Node<E>> next;

    public Node(E item, Node<E> next) {
      this.item = item;
      this.next = new AtomicReference<>(next);
    }
  }

}
