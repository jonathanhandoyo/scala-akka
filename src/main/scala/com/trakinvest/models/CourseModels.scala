package com.trakinvest.models

import com.trakinvest.models.CommonTypes._

sealed trait CourseModels

case class Chapter(id: ChapterId,
                   title: SanitizedHtml,
                   content: SanitizedHtml) extends CourseModels

case class Course(id: CourseId,
                  modules: List[Module]) extends CourseModels

case class Module(id: ModuleId,
                  chapters: List[Chapter],
                  assessment: List[Question]) extends CourseModels

case class Pack(id: PackId,
                modules: List[Module],
                price: PriceMap,
                exclusions: List[PackId]) extends CourseModels

case class Question(question: String,
                    choices: Map[Int, String],
                    answer: Int) extends CourseModels

case class UserPack() extends CourseModels
