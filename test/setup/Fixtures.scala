package test.setup

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

import models._

trait Fixtures extends Inject {
  val languageRepository = inject[LanguageRepository]
  val languagePairRepository = inject[LanguagePairRepository]

  val languages = List(
    Language(code="cs-cz", name="Czech"),
    Language(code="pl-pl", name="Polish")
  )

  def before() = {
    val deletes = Future.sequence(List(languageRepository.delete, languagePairRepository.delete))

    val insertedLanguages: Future[List[Language]] = Future.sequence(languages.map(languageRepository.insert(_)))

    insertedLanguages andThen {
      case Success(lang) => {
        val languagePair = LanguagePair(
          fromLanguage = lang(0),
          toLanguage = lang(1),
          maintainers = None)

        languagePairRepository.insert(languagePair)
      }
    }
  }
}
