package com.jonathan.actors

import akka.actor.{Actor, ActorLogging, PoisonPill, Props}
import com.jonathan.actors.ChildActor.{Move, Stop}

class ChildActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
    self ! Stop()
  }

  override def receive: Receive = {
    case m:Move => onMove(m)
    case m:Stop => onStop(m)
    case _ => onUnknown(_)
  }

  private def onMove(message: Move): Unit = {
    log.info(s"received $message")
  }

  private def onStop(message: Stop): Unit = {
    log.info(s"received $message")
    self ! PoisonPill
  }

  private def onUnknown(message: Any): Unit = {
    log.info(s"received unknown: $message")
  }
}

object ChildActor {
  def props(): Props = Props.create(classOf[ChildActor])

  case class Move()
  case class Stop()
}
