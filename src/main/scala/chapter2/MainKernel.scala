package chapter2

import akka.actor.{ActorRef, ActorSystem}
import chapter2.actors.ParentActor

object MainKernel extends App {
  val system: ActorSystem = ActorSystem("system")
  val actor: ActorRef = system.actorOf(ParentActor.props(), "parent-actor")
}