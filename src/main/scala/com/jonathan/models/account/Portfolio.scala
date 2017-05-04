package com.jonathan.models.account

import com.jonathan.models.CommonTypes.{BalanceMap, Currency, PortfolioId, UserId}

case class Portfolio(id: PortfolioId,
                     docType: String = "portfolio",
                     userId: UserId,
                     currency: Currency,
                     balance: BalanceMap
                    )

object Portfolio {
  def counter(userId: UserId): String = s"usr::$userId::portfolio::counter"
  def docId(userId: UserId, portfolioId: PortfolioId): String = s"usr::$userId::portfolio::$portfolioId"
}
