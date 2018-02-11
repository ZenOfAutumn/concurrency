package com.autumn.zen.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Handle InterruptedException by Pass
 **/
public class PassInterruptedException {

  public static void main(String[] args) throws InterruptedException {
    TimeUnit.SECONDS.sleep(400);
  }
}
