package test.setup

import models._

object Fixtures {
  val lang_cs = Language(code="cs-cz", name="Czech")
  val lang_pl = Language(code="pl-pl", name="Polish")
  val languages = Seq(lang_cs, lang_pl)

  val languagePairs = Seq(LanguagePair(fromLanguage=lang_cs, toLanguage=lang_pl, maintainers=Option(List[User]())))
}
