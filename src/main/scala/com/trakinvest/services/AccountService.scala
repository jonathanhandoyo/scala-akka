package com.trakinvest.services

import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.trakinvest.models.CommonTypes.UserId
import com.trakinvest.models.account.User
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AccountService(config: Config) extends BaseCouchbaseService(config) with LazyLogging {
  override def cluster: CouchbaseCluster = CouchbaseCluster.create(environment, config.getStringList("app.couchbase.bucket.account.clusters"))
  override def bucket: Bucket = cluster.openBucket(config.getString("app.couchbase.bucket.account.name"))

  def getUser(userId: UserId): Future[Option[User]] = {
    Future(retrieve[User](User.docId(userId)))
  }


}

object AccountService {
  def apply(): AccountService = new AccountService(ConfigFactory.defaultApplication().withFallback(ConfigFactory.defaultReference()))
  def apply(config: Config): AccountService = new AccountService(config)
}
