package com.trakinvest.models.subscription

import com.trakinvest.models.CommonTypes.{CouponId, PlanId, Status, Timestamp}

case class Coupon(id: CouponId,
                  docType: String = "coupon",
                  applicablePlanIds: Seq[PlanId],
                  description: String,
                  usageCount: Long = 0,
                  usageMax: Long = 0,
                  createdDate: Timestamp,
                  startDate: Timestamp,
                  endDate: Timestamp,
                  status: Status = "ACTIVE"
                 )

object Coupon {
  def docId(couponId: CouponId) = s"coupon::$couponId"
}
