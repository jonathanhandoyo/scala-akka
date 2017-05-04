package com.jonathan.services

import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.jonathan.models.CommonTypes.UserId
import com.jonathan.models.account.User
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AccountService(config: Config) extends AbstractCouchbaseService(config) with LazyLogging {
  override val cluster: CouchbaseCluster = CouchbaseCluster.create(environment, config.getStringList("app.couchbase.bucket.account.clusters"))
  override val bucket: Bucket = cluster.openBucket(config.getString("app.couchbase.bucket.account.name"))

  def getUser(userId: UserId): Future[Option[User]] = {
    Future(retrieve[User](User.docId(userId)))
  }
}

object AccountService {
  def apply(): AccountService = new AccountService(ConfigFactory.defaultApplication().withFallback(ConfigFactory.defaultReference()))
  def apply(config: Config): AccountService = new AccountService(config)
}
