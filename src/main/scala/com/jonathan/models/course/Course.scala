package com.jonathan.models.course

import com.jonathan.models.CommonTypes.CourseId

case class Course(id: CourseId,
                  modules: List[Module])

object Course {
  def counter(): String = s"course::counter"
  def docId(id: CourseId): String = s"course::$id"
}
