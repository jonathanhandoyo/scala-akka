package com.trakinvest.models.subscription

import com.trakinvest.models.CommonTypes.{PlanId, PriceMap, Status, Timestamp}

case class Plan(id: PlanId,
                docType: String = "plan",
                planType: String,
                name: String,
                interval: String,
                intervalCount: Long,
                price: PriceMap,
                createdDate: Timestamp,
                status: Status = "ACTIVE"
               )

object Plan {

}