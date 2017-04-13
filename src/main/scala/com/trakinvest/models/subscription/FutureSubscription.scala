package com.trakinvest.models.subscription

import com.trakinvest.models.CommonTypes.{PlanId, Status, Timestamp, UserId}

case class FutureSubscription(docType: String = "futureSubscription",
                              userId: UserId,
                              planId: PlanId,
                              createdDate: Timestamp,
                              startDate: Timestamp,
                              endDate: Timestamp,
                              status: Status = "ACTIVE"
                             )

object FutureSubscription {
  def counter(userId: UserId): String = s"subs::$userId::counter"
  def docId(userId: UserId): String = s"subs::$userId::future"
}