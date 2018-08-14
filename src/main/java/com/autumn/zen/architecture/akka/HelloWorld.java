package com.autumn.zen.architecture.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.autumn.zen.architecture.akka.Greeter.Msg;

public class HelloWorld extends UntypedActor {

  ActorRef greeter;

  @Override
  public void preStart() throws Exception {
    greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
    System.out.println("Greeter Actor Path: " + greeter.path());
    greeter.tell(Msg.GREET, getSelf());
  }

  public void onReceive(Object msg) throws Exception {
    if (msg == Msg.DONE) {

    }
  }
}
