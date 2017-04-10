package com.trakinvest.models

import com.trakinvest.models.CommonTypes._

sealed trait SubscriptionModels

case class Plan(id: PlanId,
                docType: String = "plan",
                planType: String,
                name: String,
                interval: String,
                intervalCount: Long,
                price: PriceMap,
                createdDate: Timestamp,
                status: Status = "ACTIVE"
               ) extends SubscriptionModels

case class Subscription(docType: String = "subscription",
                        userId: UserId,
                        planId: PlanId,
                        createdDate: Timestamp,
                        startDate: Timestamp,
                        endDate: Timestamp,
                        status: Status = "ACTIVE"
                       ) extends SubscriptionModels

case class FutureSubscription(docType: String = "futureSubscription",
                              userId: UserId,
                              planId: PlanId,
                              createdDate: Timestamp,
                              startDate: Timestamp,
                              endDate: Timestamp,
                              status: Status = "ACTIVE"
                             ) extends SubscriptionModels