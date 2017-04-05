package chapter2.actors

import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import chapter2.actors.ChildActor.Move
import chapter2.actors.ParentActor.Trigger

import scala.concurrent.duration._

class ParentActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
    self ! Trigger()
  }

  override def receive: Receive = {
    case m: Trigger => onTrigger(m)
    case m: Terminated => onTerminated(m)
    case _ => onUnknown(_)
  }

  override def supervisorStrategy: SupervisorStrategy = {
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => SupervisorStrategy.Resume
      case _: NullPointerException     => SupervisorStrategy.Restart
      case _: IllegalArgumentException => SupervisorStrategy.Stop
      case _: Exception                => SupervisorStrategy.Escalate
    }
  }

  private def onTerminated(message: Terminated): Unit = {
    log.info(s"received: $message")

    val child: ActorRef = context.actorOf(ChildActor.props())
    context.watch(child)
    log.info(s"restarted")
  }

  private def onTrigger(message: Trigger): Unit = {
    log.info(s"received: $message")

    val child: ActorRef = context.actorOf(ChildActor.props())
    context.watch(child)
  }

  private def onUnknown(message: Any): Unit = {
    log.info(s"received unknown: $message")
  }
}

object ParentActor {
  def props(): Props = Props.create(classOf[ParentActor])

  case class Trigger()
}