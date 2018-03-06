package com.autumn.zen.CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS Concurrent Stack
 **/
public class ConcurrentStack<E> {

  AtomicReference<Node<E>> headRef = new AtomicReference<>();

  public static void main(String[] args) throws InterruptedException {
    ConcurrentStack<String> threadStack = new ConcurrentStack<>();

    for (int i = 0; i < 10; i++) {
      new Thread() {
        @Override
        public void run() {
          threadStack.push(Thread.currentThread().getName());
        }
      }.start();
    }
    TimeUnit.SECONDS.sleep(1);

    String threadName;
    while ((threadName = threadStack.pop()) != null) {
      System.out.println(threadName);
    }

  }

  public void push(E e) {
    Node<E> newHead = new Node(e);
    Node<E> oldHead;
    do {
      oldHead = headRef.get();
      newHead.next = oldHead;
    } while (!headRef.compareAndSet(oldHead, newHead));
  }

  public E pop() {

    Node<E> oldHead;
    Node<E> newHead;

    do {
      oldHead = headRef.get();
      if (oldHead == null) {
        return null;
      }
      newHead = oldHead.next;
    } while (!headRef.compareAndSet(oldHead, newHead));
    return oldHead.e;
  }

  public static class Node<E> {

    public final E e;

    public Node<E> next;

    public Node(E e) {
      this.e = e;
    }

  }

}
