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
  val wordPairRepository = inject[WordPairRepository]

  val languages = List(
    Language(code="pl", name="Polish"),
    Language(code="cs", name="Czech")
  )

  private def getWordPairs(languagePair: LanguagePair) = List(
    WordPair(fromWord = "Chytrý telefon", toWord = "Smartfon", languagePair = languagePair),
    WordPair(fromWord = "Porouchaný", toWord = "Zepsuty", languagePair = languagePair)
  )

  def before() = {
    for {
      deletedWordPairs <- wordPairRepository.delete
      deletedLanguagePairs <- languagePairRepository.delete
      deletedLanguages <- languageRepository.delete

      insertedLanguages <- Future.sequence(languages.map(languageRepository.insert(_)))
      insertedLanguagePair <- languagePairRepository.insert(LanguagePair(
        fromLanguage = insertedLanguages(0),
        toLanguage = insertedLanguages(1)
      ))
      insertedWordPairs <- Future(getWordPairs(insertedLanguagePair).map(wordPairRepository.insert(_)))
    } yield Unit
  }
}
