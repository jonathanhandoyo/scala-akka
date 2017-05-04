package com.jonathan.services

import com.jonathan.models.CommonTypes.UserId
import com.jonathan.models.subscription.Subscription
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Future
import scala.concurrent.duration._

class SubscriptionServiceSpec
  extends WordSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures
    with LazyLogging {

  val timeout: Timeout = Timeout(10 minute)

  val subscriptionService: SubscriptionService = SubscriptionService()

  "SubscriptionService" should {
    "be able to retrieve Subscription by UserId" in {
      val userId: UserId = 5703
      val eventualMaybeSubscription: Future[Option[Subscription]] = subscriptionService.getSubscription(userId)

      whenReady(eventualMaybeSubscription, timeout) { maybeSubscription =>
        maybeSubscription.shouldBe(defined)
        maybeSubscription.get.userId.shouldEqual(userId)
      }
    }
  }
}
