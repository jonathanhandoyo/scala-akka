package com.jonathan.models.metric

import com.jonathan.models.CommonTypes.{PortfolioId, Timestamp, UserId}

case class Metric(userId: UserId,
                  portfolioId: PortfolioId,
                  code: String,
                  counter: Long,
                  startTs: Timestamp
                 )