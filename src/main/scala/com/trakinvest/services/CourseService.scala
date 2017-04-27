package com.trakinvest.services

import com.couchbase.client.java.{Bucket, CouchbaseCluster}
import com.trakinvest.models.CommonTypes.CourseId
import com.trakinvest.models.course.Course
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class CourseService(config: Config) extends BaseCouchbaseService(config) with LazyLogging {
  override def cluster: CouchbaseCluster = CouchbaseCluster.create(environment, config.getStringList("app.couchbase.bucket.transactions.clusters"))
  override def bucket: Bucket = cluster.openBucket(config.getString("app.couchbase.bucket.transactions.name"))

  def getCourse(courseId: CourseId): Future[Option[Course]] = {
    Future(retrieve[Course](Course.docId(courseId)))
  }

  def updateCourse(course: Course): Future[Option[Course]] = {
    Future(upsert[Course](Course.docId(course.id), course))
  }

  def createCourse(course: Course): Future[Option[Course]] = {
    Future {
      val id: CourseId = nextCounter(Course.counter())
      upsert[Course](Course.docId(id), course.copy(id = id))
    }
  }
}

object CourseService {
  def apply(): CourseService = new CourseService(ConfigFactory.defaultApplication().withFallback(ConfigFactory.defaultReference()))
  def apply(config: Config): CourseService = new CourseService(config)
}