package com.trakinvest.actors

import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import com.trakinvest.actors.ParentActor.Echo
import com.trakinvest.services.SubscriptionService

import scala.concurrent.duration._

class ParentActor(subscriptionService: SubscriptionService) extends Actor with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
  }

  override def receive: Receive = {
    case m: Echo => onEcho(m)
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

  private def onEcho(message: Echo): Unit = {
    log.info(s"received: $message")
  }

  private def onTerminated(message: Terminated): Unit = {
    log.info(s"received: $message")

    val child: ActorRef = context.actorOf(ChildActor.props())
    context.watch(child)
    log.info(s"restarted")
  }

  private def onUnknown(message: Any): Unit = {
    log.info(s"received unknown: $message")
  }
}

object ParentActor {
  def props(subscriptionService: SubscriptionService): Props = Props.create(classOf[ParentActor], subscriptionService)

  case class Trigger()
  case class Echo()
}