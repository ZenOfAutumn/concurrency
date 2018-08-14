package com.autumn.zen.unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

public class UnsafeSinglton {

  public Unsafe getInstance() {
    return UnsafeHolder.THE_UNSAFE;
  }

  private static class UnsafeHolder {

    private static final Unsafe THE_UNSAFE;

    static {
      try {
        final PrivilegedExceptionAction<Unsafe> action = () -> {
          Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
          theUnsafe.setAccessible(true);
          return (Unsafe) theUnsafe.get(null);
        };
        THE_UNSAFE = AccessController.doPrivileged(action);
      } catch (Exception e) {
        throw new RuntimeException("Unable to load unsafe", e);
      }
    }
  }



}
