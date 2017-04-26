package com.trakinvest.models.subscription

import com.trakinvest.models.CommonTypes.{PlanId, PriceMap, Status, Timestamp}
import com.trakinvest.models.subscription.Plan.PlanType

case class Plan(id: PlanId,
                docType: String = "plan",
                planType: PlanType,
                name: String,
                interval: String,
                intervalCount: Long,
                price: PriceMap,
                createdDate: Timestamp,
                status: Status = "ACTIVE"
               )

object Plan {

  sealed abstract class PlanType(val index: Int, val name: String)

  case object Basic extends PlanType(0, "basic")
  case object BasicPlus extends PlanType(1, "basic-plus")
  case object Premium extends PlanType(2, "premium")
  case object Certificate extends PlanType(3, "certificate")
  case object Pro extends PlanType(4, "pro")

  def ofType(name: String): PlanType = {
    name match {
      case "basic" => Basic
      case "basic-plus" => BasicPlus
      case "premium" => Premium
      case "certificate" => Certificate
      case "pro" => Pro
      case _ => throw new IllegalArgumentException(s"unknown name: $name")
    }
  }
}