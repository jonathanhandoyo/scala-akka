package com.trakinvest.services

import com.trakinvest.models.CommonTypes.{PortfolioId, UserId}
import com.trakinvest.models.metric.Metric
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Future
import scala.concurrent.duration._

class MetricServiceSpec
  extends WordSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures
    with LazyLogging {

  val timeout: Timeout = Timeout(10 minutes)

  val metricService: MetricService = MetricService()

  "MetricService" should {
    "be able to retrieve Metric" in {
      val userId: UserId = 5703
      val portfolioId: PortfolioId = 5700
      val code: String = "TRADE"

      val eventualMetric: Future[Metric] = metricService.getMetric(userId, portfolioId, code)

      whenReady(eventualMetric, timeout) { metric =>
        metric.userId shouldEqual userId
        metric.portfolioId shouldEqual portfolioId
        metric.code shouldEqual code
      }
    }
  }

}
