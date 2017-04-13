package com.trakinvest.models.course

case class Question(question: String,
                    choices: Map[Int, String],
                    answer: Int)

object Question {}
