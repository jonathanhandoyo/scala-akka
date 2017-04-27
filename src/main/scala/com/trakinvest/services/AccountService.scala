package com.trakinvest.services

import com.trakinvest.models.CommonTypes.UserId
import com.trakinvest.models.account.User
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class AccountService(couchbaseService: BaseCouchbaseService) extends LazyLogging {

  def getUser(userId: UserId): Future[Option[User]] = {
    Future(couchbaseService.retrieve[User](User.docId(userId)))
  }

}

object AccountService {
  def apply(couchbaseService: BaseCouchbaseService): AccountService = new AccountService(couchbaseService)
}
