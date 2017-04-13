package com.trakinvest

import akka.actor.{ActorRef, ActorSystem}
import com.trakinvest.actors.ParentActor
import com.trakinvest.actors.ParentActor.Echo
import com.trakinvest.models.Chapter
import com.trakinvest.services.{CouchbaseService, SubscriptionService}
import com.trakinvest.utils.JsonUtil

object MainKernel extends App {
  val couchbaseService: CouchbaseService = CouchbaseService()
  val subscriptionService: SubscriptionService = SubscriptionService(couchbaseService)

  val system: ActorSystem = ActorSystem.create("system")
  val actor: ActorRef = system.actorOf(ParentActor.props(subscriptionService), "parent-actor")
  actor ! Echo()

  system.terminate()
}