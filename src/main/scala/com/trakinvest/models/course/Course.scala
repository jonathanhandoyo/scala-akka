package com.trakinvest.models.course

import com.trakinvest.models.CommonTypes.CourseId

case class Course(id: CourseId,
                  modules: List[Module])

object Course {}
