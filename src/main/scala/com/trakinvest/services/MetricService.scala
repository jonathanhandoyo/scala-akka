package com.trakinvest.services

import com.trakinvest.models.CommonTypes.{PortfolioId, UserId}
import com.trakinvest.models.metric.Metric
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MetricService(mongoService: BaseMongoService) extends BaseJacksonTrait with LazyLogging {
  private val collection: MongoCollection[Document] = mongoService.database.getCollection("metrics")

  def getMetric(code: String, userId: UserId, portfolioId: PortfolioId): Future[Metric] = {
    collection
      .find(
        Filters.and(
          Filters.equal("code", code),
          Filters.equal("userId", userId.toString),
          Filters.equal("portfolioId", portfolioId.toString)
        )
      )
      .head()
      .map(d => fromJson[Metric](d.toJson()))
  }
}

object MetricService {
  def apply(mongo: BaseMongoService): MetricService = new MetricService(mongo: BaseMongoService)
}
