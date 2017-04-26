package com.trakinvest

import akka.actor.{ActorRef, ActorSystem}
import com.trakinvest.actors.ParentActor
import com.trakinvest.actors.ParentActor.Echo
import com.trakinvest.services.{BaseCouchbaseService, BaseMongoService, MetricService, SubscriptionService}

object MainKernel extends App {
  val couchbaseService: BaseCouchbaseService = BaseCouchbaseService()
  val mongoService: BaseMongoService = BaseMongoService()

  val subscriptionService: SubscriptionService = SubscriptionService(couchbaseService)
  val metricService: MetricService = MetricService(mongoService)

  val system: ActorSystem = ActorSystem.create("system")
  val actor: ActorRef = system.actorOf(ParentActor.props(metricService, subscriptionService), "parent-actor")
  actor ! Echo()

  system.terminate()
}