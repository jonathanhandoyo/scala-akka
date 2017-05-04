package com.jonathan.models

import org.mongodb.scala.bson.ObjectId

object CommonTypes extends Enumeration {
  type ChapterId = String
  type CouponId = String
  type CourseId = Long
  type Currency = String
  type MetricId = ObjectId
  type ModuleId = String
  type PackId = String
  type PlanId = Long
  type PortfolioId = Long
  type UserId = Long
  type UserPackId = String

  type Status = String
  type Timestamp = Long

  type BalanceMap = Map[String, Double]
  type PriceMap = Map[String, Double]
  type RateMap = Map[String, Double]

  type SanitizedHtml = String
}
