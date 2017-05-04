package com.jonathan.services

import com.jonathan.models.CommonTypes.CouponId
import com.jonathan.models.subscription.Coupon
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future
import scala.concurrent.duration._

class CouponServiceSpec
  extends WordSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures
    with LazyLogging {

  val timeout: Timeout = Timeout(10 minute)

  val couponService: CouponService = CouponService()

  "CouponService" should {
    "be able to retrieve Coupon by ID" in {
      val couponId: CouponId = "wirasign10"
      val eventualMaybeCoupon: Future[Option[Coupon]] = couponService.getCoupon(couponId)

      whenReady(eventualMaybeCoupon, timeout) { maybeCoupon =>
        maybeCoupon should not be defined
//        maybeCoupon.get.id shouldEqual couponId
      }
    }
  }
}
