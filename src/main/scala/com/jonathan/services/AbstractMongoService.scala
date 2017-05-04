package com.jonathan.services

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.{MongoClient, MongoDatabase, MongoCollection}

abstract class AbstractMongoService(config: Config) extends AbstractJacksonService with LazyLogging {
  protected[services] val client: MongoClient = MongoClient(config.getString("app.mongodb.uri"))
  protected[services] val database: MongoDatabase
  protected[services] val collection: MongoCollection[Document]
}