package chapter1.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import chapter1.actors.ChildActor.Move
import chapter1.actors.ParentActor.Trigger

class ParentActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
    self ! Trigger()
  }

  override def receive: Receive = {
    case m: Trigger => onTrigger(m)
    case _ => onUnknown(_)
  }

  private def onTrigger(message: Trigger): Unit = {
    log.info(s"received: $message")
    val child: ActorRef = context.actorOf(ChildActor.props())
    child ! Move()
  }

  private def onUnknown(message: Any): Unit = {
    log.info(s"received unknown: $message")
  }
}

object ParentActor {
  def props(): Props = Props.create(classOf[ParentActor])

  case class Trigger()
}