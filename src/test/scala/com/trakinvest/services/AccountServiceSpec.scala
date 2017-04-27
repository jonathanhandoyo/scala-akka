package com.trakinvest.services

import akka.testkit.DefaultTimeout
import com.trakinvest.models.account.User
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.Span
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

  val couchbaseService: BaseCouchbaseService = BaseCouchbaseService()
  val mongoService: BaseMongoService = BaseMongoService()

  val accountService: AccountService = AccountService(couchbaseService)

  "AccountService" should {
    "be able to retrieve User by ID" in {
      val eventualMaybeUser: Future[Option[User]] = accountService.getUser(5703)

      whenReady(eventualMaybeUser, timeout) { maybeUser =>
        maybeUser shouldBe defined
        maybeUser.get.id shouldEqual 5703
      }
    }
  }
}
