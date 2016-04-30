package test.setup

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
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
    val deletes: Future[List[Int]] = Future.sequence(List(languageRepository.delete, languagePairRepository.delete))

    val insertedLanguages: Future[List[Language]] = deletes.flatMap {
      case _ => Future.sequence(languages.map(languageRepository.insert(_)))
    }

    val insertedLanguagePairs: Future[Int] = insertedLanguages.flatMap {
      case languages => {
        val languagePair = LanguagePair(
          fromLanguage = languages(0),
          toLanguage = languages(1),
          maintainers = None)

        languagePairRepository.insert(languagePair)
      }
    }

    Await.result(insertedLanguages, 1 seconds);
  }
}
