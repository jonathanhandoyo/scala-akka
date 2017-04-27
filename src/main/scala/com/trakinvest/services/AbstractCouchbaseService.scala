package com.trakinvest.services

import com.couchbase.client.java.document.JsonDocument
import com.couchbase.client.java.env.{CouchbaseEnvironment, DefaultCouchbaseEnvironment}
import com.couchbase.client.java.query.{N1qlQuery, N1qlQueryResult, N1qlQueryRow}
import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.typesafe.config.Config

import scala.collection.JavaConverters._
import scala.util.Try

abstract class AbstractCouchbaseService(config: Config) extends AbstractJacksonTrait {

  protected[services] val bucket: Bucket
  protected[services] val cluster: CouchbaseCluster
  protected[services] val environment: CouchbaseEnvironment = DefaultCouchbaseEnvironment.builder()
    .connectTimeout(config.getLong("app.couchbase.environment.connect-timeout"))
    .maxRequestLifetime(config.getLong("app.couchbase.environment.max-request-lifetime"))
    .autoreleaseAfter(config.getLong("app.couchbase.environment.auto-release-after"))
    .build()

  protected[services] def delete[T](docId: String)(implicit m: Manifest[T]): Option[T] = {
    val json: String = bucket.remove(docId).content().toString
    Try(fromJson[T](json)).toOption
  }

  protected[services] def retrieve[T](docId: String)(implicit m: Manifest[T]): Option[T] = {
    val json: String = bucket.get(docId, classOf[JsonDocument]).content().toString
    Try(fromJson[T](json)).toOption
  }

  protected[services] def upsert[T](docId: String, t: T)(implicit m: Manifest[T]): Option[T] = {
    val document: JsonDocument = JsonDocument.create(docId, toJsonObject(t))
    val upserted: JsonDocument = bucket.upsert(document)
    val json: String = upserted.content().toString
    Try(fromJson[T](json)).toOption
  }

  protected[services] def currentCounter(counterId: String): Long = {
    bucket.counter(counterId, 1).content().toLong
  }

  protected[services] def nextCounter(counterId: String): Long = {
    bucket.counter(counterId, 1, 1).content().toLong
  }

  protected[services] def queryForList[T](query: N1qlQuery, mapper: N1qlQueryRow => T): Option[List[T]] = {
    val result: N1qlQueryResult = bucket.query(query)
    result.status() match {
      case "xxx" => Try(result.allRows().asScala.map(mapper).toList).toOption
      case "yyy" => None
    }
  }

  protected[services] def queryForObject[T](query: N1qlQuery, mapper: N1qlQueryRow => T): Option[T] = {
    Try(mapper(bucket.query(query).allRows().asScala.head)).toOption
  }
}