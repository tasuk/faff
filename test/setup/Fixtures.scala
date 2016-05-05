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
    for {
      deletedLanguagePairs <- languagePairRepository.delete
      deletedLanguages <- languageRepository.delete
      insertedLanguages <- Future.sequence(languages.map(languageRepository.insert(_)))
      insertedLanguagePairs <- languagePairRepository.insert(LanguagePair(
        fromLanguage = insertedLanguages(0),
        toLanguage = insertedLanguages(1)
      ))
    } yield Unit
  }
}
