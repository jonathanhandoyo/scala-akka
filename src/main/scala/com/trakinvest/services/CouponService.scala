package com.trakinvest.services

import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.trakinvest.models.CommonTypes.CouponId
import com.trakinvest.models.subscription.Coupon
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CouponService(config: Config) extends AbstractCouchbaseService(config) with LazyLogging {
  override val cluster: CouchbaseCluster = CouchbaseCluster.create(environment, config.getStringList("app.couchbase.bucket.transactions.clusters"))
  override val bucket: Bucket = cluster.openBucket(config.getString("app.couchbase.bucket.transactions.name"))

  def getCoupon(couponId: CouponId): Future[Option[Coupon]] = {
    Future {
      retrieve[Coupon](Coupon.docId(couponId)) match {
        case Some(coupon) =>
          val now: Long = DateTime.now().toDate.getTime
          coupon match {
            case c if c.status != "ACTIVE"        => None
            case c if now < c.startDate           => None
            case c if now > c.endDate             => None
            case c if c.usageCount >= c.usageMax  => None
            case c => Some(c)
          }

        case None => None
      }
    }
  }
}

object CouponService {
  def apply(): CouponService = new CouponService(ConfigFactory.defaultApplication().withFallback(ConfigFactory.defaultReference()))
  def apply(config: Config): CouponService = new CouponService(config)
}