package com.trakinvest.services

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.document.json.JsonObject
import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.typesafe.config.{Config, ConfigFactory}
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, parse, compactRender}

import scala.util.Try

class CouchbaseService(config: Config) {
  implicit val formats = DefaultFormats

  protected[services] lazy val cluster: CouchbaseCluster = CouchbaseCluster.create(config.getString("couchbase.cluster"))
  protected[services] lazy val bucket: Bucket = cluster.openBucket(config.getString("couchbase.bucket"))

  protected[services] def delete[T](docId: String)(implicit m: Manifest[T]): Option[T] = {
    Try(parse(bucket.remove(docId).content().toString).extract[T]).toOption
  }

  protected[services] def retrieve[T](docId: String)(implicit m: Manifest[T]): Option[T] = {
    Try(parse(bucket.get(docId, classOf[JsonDocument]).content().toString).extract[T]).toOption
  }

  protected[services] def upsert[T](docId: String, t: T)(implicit m: Manifest[T]): Option[T] = {
    val formattedJsonDocument: JsonDocument = JsonDocument.create(docId, JsonObject.fromJson(compactRender(decompose(t))))
    val upsertedJsonDocument: JsonDocument = bucket.upsert(formattedJsonDocument)
    Try(parse(upsertedJsonDocument.content().toString).extract[T]).toOption
  }

  protected[services] def currentCounter(counterId: String): Long = {
    bucket.counter(counterId, 1).content().toLong
  }

  protected[services] def nextCounter(counterId: String): Long = {
    bucket.counter(counterId, 1, 1).content().toLong
  }
}

object CouchbaseService {
  def apply(): CouchbaseService = new CouchbaseService(ConfigFactory.defaultApplication())
  def apply(config: Config): CouchbaseService = new CouchbaseService(config)
}