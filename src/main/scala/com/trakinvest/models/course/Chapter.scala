package com.trakinvest.models.course

import com.trakinvest.models.CommonTypes.{ChapterId, SanitizedHtml}

case class Chapter(id: ChapterId,
                   title: SanitizedHtml,
                   content: SanitizedHtml)

case class ChapterRef(index: Int,
                      chapter: ChapterId)

object Chapter {}
