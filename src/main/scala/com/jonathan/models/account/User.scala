package com.jonathan.models.account

import com.jonathan.models.CommonTypes.{Timestamp, UserId, Status}

case class User(id: UserId,
                docType: String = "user",
                firstName: String,
                lastName: String,
                email: String,
                password: String,
                gender: String,
                createdDate: Timestamp,
                status: Status = "ACTIVE"
               )

object User {
  def counter(): String = s"usr::counter"
  def docId(userId: UserId): String = s"usr::$userId"
}