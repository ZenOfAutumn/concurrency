package com.autumn.zen.pattern.singleton;

public class StaticHolderSingleton {

  private StaticHolderSingleton() {
  }

  public static StaticHolderSingleton getInstance() {
    return SingletonHolder.INSTANCE;
  }

  private static class SingletonHolder {

    private static final StaticHolderSingleton INSTANCE = new StaticHolderSingleton();
  }

}
