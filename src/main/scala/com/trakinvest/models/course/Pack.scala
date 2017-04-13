package com.trakinvest.models.course

import com.trakinvest.models.CommonTypes.{PackId, PriceMap}

case class Pack(id: PackId,
                modules: List[ModuleRef],
                price: PriceMap,
                exclusions: List[PackId])

object Pack {}
