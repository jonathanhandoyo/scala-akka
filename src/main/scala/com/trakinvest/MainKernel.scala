package com.trakinvest

import akka.actor.{ActorRef, ActorSystem}
import com.trakinvest.actors.ParentActor
import com.trakinvest.actors.ParentActor.Echo
import com.trakinvest.services.{MetricService, SubscriptionService}

object MainKernel extends App {
  val subscriptionService: SubscriptionService = SubscriptionService()
  val metricService: MetricService = MetricService()

  val system: ActorSystem = ActorSystem.create("system")
  val actor: ActorRef = system.actorOf(ParentActor.props(metricService, subscriptionService), "parent-actor")
  actor ! Echo()

  system.terminate()
}