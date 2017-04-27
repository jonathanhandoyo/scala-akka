package com.trakinvest.actors

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import com.trakinvest.actors.ParentActor.Echo
import com.trakinvest.services.{BaseCouchbaseService, BaseMongoService, MetricService, SubscriptionService}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class ParentActorSpec
  extends TestKit(ActorSystem("system"))
    with DefaultTimeout
    with ImplicitSender
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll {

  //implicits here
  implicit val executionContext: ExecutionContext = system.dispatcher

  //com.trakinvest.services here
  val mongoService: BaseMongoService = BaseMongoService()

  val metricService: MetricService = MetricService(mongoService)
  val subscriptionService: SubscriptionService = SubscriptionService()

  //com.trakinvest.actors here
  var parentActor: ActorRef = _

  override protected def beforeAll: Unit = {
    parentActor = system.actorOf(ParentActor.props(metricService, subscriptionService))
  }

  override protected def afterAll(): Unit = {
    shutdown()
  }

  "a Test" should {
    "run" in {
      parentActor ! Echo()
      expectNoMsg(10 minutes)
    }
  }
}
