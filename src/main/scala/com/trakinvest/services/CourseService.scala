package com.trakinvest.services

import com.trakinvest.models.CommonTypes.CourseId
import com.trakinvest.models.Course
import com.typesafe.scalalogging.LazyLogging

class CourseService(couchbaseService: CouchbaseService) extends LazyLogging {
  import CourseService._

  def getCourse(courseId: CourseId): Option[Course] = {
    couchbaseService.retrieve[Course](docIdForCourse(courseId))
  }

  def updateCourse(course: Course): Option[Course] = {
    couchbaseService.upsert[Course](docIdForCourse(course.id), course)
  }

  def createCourse(course: Course): Option[Course] = {
    val id: CourseId = couchbaseService.nextCounter(counterForCourse())
    couchbaseService.upsert[Course](docIdForCourse(id), course.copy(id = id))
  }
}

object CourseService {
  def apply(couchbaseService: CouchbaseService): CourseService = new CourseService(couchbaseService)

  def counterForCourse(): String = s"course::counter"
  def docIdForCourse(courseId: CourseId): String = s"course::$courseId"
}