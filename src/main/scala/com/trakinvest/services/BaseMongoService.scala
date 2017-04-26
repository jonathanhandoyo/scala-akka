package com.trakinvest.services

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.{MongoClient, MongoDatabase}

class BaseMongoService(config: Config) extends BaseJacksonTrait with LazyLogging {
  protected[services] lazy val client: MongoClient = MongoClient(config.getString("app.mongodb.uri"))
  protected[services] lazy val database: MongoDatabase = client.getDatabase(config.getString("app.mongodb.db-name"))
}

object BaseMongoService {
  def apply(): BaseMongoService = new BaseMongoService(ConfigFactory.defaultApplication())
  def apply(config: Config): BaseMongoService = new BaseMongoService(config)
}
