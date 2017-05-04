package com.jonathan.models.course

case class Question(question: String,
                    choices: Map[Int, String],
                    answer: Int)

object Question {}
