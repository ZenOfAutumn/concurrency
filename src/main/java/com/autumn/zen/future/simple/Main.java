package com.autumn.zen.future.simple;

public class Main {

  public static void main(String[] args) {

    Client client = new Client();

    FutureData futureData = client.call("lee");

    long start = System.currentTimeMillis();

    System.out.println(futureData.getResult());

    System.out.println("Elapse time: " + (System.currentTimeMillis() - start));

  }

}
