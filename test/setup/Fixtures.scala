package test.setup

import models._

object Fixtures {
  val lang_cs = Language(id=Option(1), code="cs-cz", name="Czech")
  val lang_pl = Language(id=Option(2), code="pl-pl", name="Polish")
  val languages = List(lang_cs, lang_pl)

  val languagePairs = List(LanguagePair(id=Option(1), fromLanguage=lang_cs, toLanguage=lang_pl, maintainers=Option(List[User]())))
}
