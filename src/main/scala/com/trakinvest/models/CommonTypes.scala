package com.trakinvest.models

object CommonTypes extends Enumeration {
  type ChapterId = String
  type CourseId = Long
  type ModuleId = String
  type PackId = String
  type PlanId = Long
  type PortfolioId = Long
  type UserId = Long
  type UserPackId = String

  type Status = String
  type Timestamp = Long

  type PriceMap = Map[String, Double]
  type RateMap = Map[String, Double]

  type SanitizedHtml = String
}
