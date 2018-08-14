package com.autumn.zen.architecture.akka;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {

  public void onReceive(Object msg) throws Exception {
    if (msg == Msg.GREET) {
      System.out.println("Hello World");
      getSender().tell(Msg.DONE, getSelf());

    } else {
      unhandled(msg);
    }
  }

  public static enum Msg {
    GREET, DONE;
  }
}
