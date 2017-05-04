package com.jonathan.actors

import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy, Terminated}
import com.jonathan.actors.ParentActor.Echo
import com.jonathan.services.{MetricService, SubscriptionService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ParentActor(metricService: MetricService,
                  subscriptionService: SubscriptionService)
  extends Actor
    with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
  }

  override def receive: Receive = {
    case m: Echo        => onEcho(m)
    case m: Terminated  => onTerminated(m)
    case _              => onUnknown(_)
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
    metricService.getMetric(5703, 5700, "TRADE")
      .map(metric => {
        log.info(s"$metric")
      })
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
  def props(metricService: MetricService, subscriptionService: SubscriptionService): Props = Props.create(classOf[ParentActor], metricService, subscriptionService)

  case class Trigger()
  case class Echo()
}