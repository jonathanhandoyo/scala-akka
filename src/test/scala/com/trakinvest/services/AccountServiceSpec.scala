package com.trakinvest.services

import com.trakinvest.models.CommonTypes.UserId
import com.trakinvest.models.account.User
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Future
import scala.concurrent.duration._

class AccountServiceSpec
  extends WordSpecLike
    with Matchers
    with BeforeAndAfterAll
    with ScalaFutures
    with LazyLogging {

  val timeout: Timeout = Timeout(10 minute)

  val accountService: AccountService = AccountService()

  "AccountService" should {
    "be able to retrieve User by ID" in {
      val userId: UserId = 5703
      val eventualMaybeUser: Future[Option[User]] = accountService.getUser(userId)

      whenReady(eventualMaybeUser, timeout) { maybeUser =>
        maybeUser shouldBe defined
        maybeUser.get.id shouldEqual userId
      }
    }
  }
}
