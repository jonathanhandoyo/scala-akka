package com.trakinvest.services

import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.trakinvest.models.CommonTypes.{PlanId, UserId}
import com.trakinvest.models.subscription.Subscription
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SubscriptionService(config: Config) extends AbstractCouchbaseService(config) with LazyLogging {
  override val cluster: CouchbaseCluster = CouchbaseCluster.create(environment, config.getStringList("app.couchbase.bucket.transactions.clusters"))
  override val bucket: Bucket = cluster.openBucket(config.getString("app.couchbase.bucket.transactions.name"))

  def getSubscription(userId: UserId): Future[Option[Subscription]] = {
    Future(retrieve[Subscription](Subscription.docId(userId)))
  }

  def updateSubscription(subscription: Subscription): Future[Option[Subscription]] = {
    Future(upsert[Subscription](Subscription.docId(subscription.userId), subscription))
  }

  def createSubscription(userId: UserId, planId: PlanId): Future[Option[Subscription]] = {
    Future {
      val subscription: Subscription = Subscription(
        userId = userId,
        planId = planId,
        createdDate = DateTime.now().getMillis,
        startDate = DateTime.now().withTimeAtStartOfDay().getMillis,
        endDate = DateTime.now().plusDays(30).withTimeAtStartOfDay().getMillis
      )
      upsert[Subscription](Subscription.docId(subscription.userId), subscription)
    }
  }
}

object SubscriptionService {
  def apply(): SubscriptionService = new SubscriptionService(ConfigFactory.defaultApplication().withFallback(ConfigFactory.defaultReference()))
  def apply(config: Config): SubscriptionService = new SubscriptionService(config)
}