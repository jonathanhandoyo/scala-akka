package com.jonathan.models.subscription

import com.jonathan.models.CommonTypes.{PlanId, Status, Timestamp, UserId}

case class Subscription(docType: String = "subscription",
                        userId: UserId,
                        planId: PlanId,
                        createdDate: Timestamp,
                        startDate: Timestamp,
                        endDate: Timestamp,
                        status: Status = "ACTIVE"
                       )

object Subscription {
  def counter(): String = s"subs::counter"
  def docId(userId: UserId): String = s"subs::$userId"
}
