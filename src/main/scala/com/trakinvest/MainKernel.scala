package com.trakinvest

import akka.actor.{ActorRef, ActorSystem}
import com.trakinvest.actors.ParentActor
import com.trakinvest.services.{CouchbaseService, SubscriptionService}

object MainKernel extends App {
  val couchbaseService: CouchbaseService = CouchbaseService()
  val subscriptionService: SubscriptionService = SubscriptionService(couchbaseService)

  val system: ActorSystem = ActorSystem.create("system")
  val actor: ActorRef = system.actorOf(ParentActor.props(subscriptionService), "parent-actor")
}