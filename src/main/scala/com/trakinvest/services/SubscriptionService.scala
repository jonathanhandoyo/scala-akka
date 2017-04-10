package com.trakinvest.services

import com.trakinvest.models.CommonTypes.{PlanId, UserId}
import com.trakinvest.models.Subscription
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

class SubscriptionService(couchbaseService: CouchbaseService) extends LazyLogging {
  import SubscriptionService._

  def getSubscription(userId: UserId): Option[Subscription] = {
    couchbaseService.retrieve[Subscription](docIdForSubscription(userId))
  }

  def updateSubscription(subscription: Subscription): Option[Subscription] = {
    couchbaseService.upsert[Subscription](docIdForSubscription(subscription.userId), subscription)
  }

  def createSubscription(userId: UserId, planId: PlanId): Option[Subscription] = {
    val subscription: Subscription = Subscription(
      userId = userId,
      planId = planId,
      createdDate = DateTime.now().getMillis,
      startDate = DateTime.now().withTimeAtStartOfDay().getMillis,
      endDate = DateTime.now().plusDays(30).withTimeAtStartOfDay().getMillis
    )
    couchbaseService.upsert[Subscription](docIdForSubscription(userId), subscription)
  }
}

object SubscriptionService {
  def apply(couchbaseService: CouchbaseService): SubscriptionService = new SubscriptionService(couchbaseService)

  def counterForSubscription(): String = s"subs::counter"
  def counterForSubscriptionHistory(userId: UserId): String = s"subs::$userId::counter"
  def docIdForFutureSubscription(userId: UserId): String = s"subs::$userId::future"
  def docIdForSubscription(userId: UserId): String = s"subs::$userId"
}