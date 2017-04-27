package com.trakinvest.services

import com.trakinvest.models.CommonTypes.{PortfolioId, UserId}
import com.trakinvest.models.metric.Metric
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.{MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class MetricService(config: Config) extends AbstractMongoService(config) with LazyLogging {
  override val database: MongoDatabase = client.getDatabase(config.getString("app.mongodb.db.analytics"))
  override val collection: MongoCollection[Document] = database.getCollection("metrics")

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
  def apply(): MetricService = new MetricService(ConfigFactory.defaultApplication().withFallback(ConfigFactory.defaultReference()))
  def apply(config: Config): MetricService = new MetricService(config)
}
