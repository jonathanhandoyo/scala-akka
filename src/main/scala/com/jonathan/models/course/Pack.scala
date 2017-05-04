package com.jonathan.models.course

import com.jonathan.models.CommonTypes.{PackId, PriceMap}

case class Pack(id: PackId,
                modules: List[ModuleRef],
                price: PriceMap,
                exclusions: List[PackId])

object Pack {}
