package com.jonathan.models.course

import com.jonathan.models.CommonTypes.{ChapterId, SanitizedHtml}

case class Chapter(id: ChapterId,
                   title: SanitizedHtml,
                   content: SanitizedHtml)

case class ChapterRef(index: Int,
                      chapter: ChapterId)

object Chapter {}
