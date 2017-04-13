package com.trakinvest.models.course

import com.trakinvest.models.CommonTypes.ModuleId

case class Module(id: ModuleId,
                  chapters: List[ChapterRef],
                  assessment: List[Question])

case class ModuleRef(index: Int,
                     module: ModuleId)

object Module {}
