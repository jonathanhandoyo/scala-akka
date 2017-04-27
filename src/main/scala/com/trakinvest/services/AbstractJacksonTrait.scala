package com.trakinvest.services

import com.couchbase.client.java.document.json.JsonObject
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.reflect._

trait AbstractJacksonTrait {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def fromJson[T](json: String)(implicit m : Manifest[T]): T = mapper.readValue[T](json, classTag[T].runtimeClass.asInstanceOf[Class[T]])

  def toJson(value: Map[Symbol, Any]): String = toJson(value map { case (k, v) => k.name -> v})
  def toJson(value: Any): String = mapper.writeValueAsString(value)

  def toJsonObject(value: Any): JsonObject = JsonObject.fromJson(toJson(value))

  def toMap[V](json: String)(implicit m: Manifest[V]): Map[String, V] = fromJson[Map[String,V]](json)
}
