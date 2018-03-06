package com.autumn.zen.object.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdate {

  private static AtomicIntegerFieldUpdater<User> userAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater
    .newUpdater(User.class, "age");

  public static void main(String[] args) {
    User user = new User(10, "john");
    userAtomicIntegerFieldUpdater.compareAndSet(user, 10, 20);
    System.out.println(userAtomicIntegerFieldUpdater.get(user));
    userAtomicIntegerFieldUpdater.getAndIncrement(user);
    System.out.println(userAtomicIntegerFieldUpdater.get(user));
  }

  private static class User {

    public volatile int age;

    private String name;

    private User(int age, String name) {
      this.age = age;
      this.name = name;
    }
  }

}
