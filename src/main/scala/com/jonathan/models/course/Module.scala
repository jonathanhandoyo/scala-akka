package com.jonathan.models.course

import com.jonathan.models.CommonTypes.ModuleId

case class Module(id: ModuleId,
                  chapters: List[ChapterRef],
                  assessment: List[Question])

case class ModuleRef(index: Int,
                     module: ModuleId)

object Module {}
