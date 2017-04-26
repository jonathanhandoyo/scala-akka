package com.trakinvest.services

import com.trakinvest.models.CommonTypes.CourseId
import com.trakinvest.models.course.Course
import com.typesafe.scalalogging.LazyLogging

class CourseService(couchbaseService: BaseCouchbaseService) extends LazyLogging {

  def getCourse(courseId: CourseId): Option[Course] = {
    couchbaseService.retrieve[Course](Course.docId(courseId))
  }

  def updateCourse(course: Course): Option[Course] = {
    couchbaseService.upsert[Course](Course.docId(course.id), course)
  }

  def createCourse(course: Course): Option[Course] = {
    val id: CourseId = couchbaseService.nextCounter(Course.counter())
    couchbaseService.upsert[Course](Course.docId(id), course.copy(id = id))
  }
}

object CourseService {
  def apply(couchbaseService: BaseCouchbaseService): CourseService = new CourseService(couchbaseService)
}