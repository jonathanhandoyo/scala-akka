package com.jonathan

import akka.actor.{ActorRef, ActorSystem}
import com.jonathan.actors.ParentActor
import com.jonathan.actors.ParentActor.Echo
import com.jonathan.services.{MetricService, SubscriptionService}

object MainKernel extends App {
  val subscriptionService: SubscriptionService = SubscriptionService()
  val metricService: MetricService = MetricService()

  val system: ActorSystem = ActorSystem.create("system")
  val actor: ActorRef = system.actorOf(ParentActor.props(metricService, subscriptionService), "parent-actor")
  actor ! Echo()

  system.terminate()
}