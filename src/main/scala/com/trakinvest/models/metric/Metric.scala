package com.trakinvest.models.metric

import com.trakinvest.models.CommonTypes.{PortfolioId, Timestamp, UserId}

case class Metric(userId: UserId,
                  portfolioId: PortfolioId,
                  code: String,
                  counter: Long,
                  startTs: Timestamp
                 )