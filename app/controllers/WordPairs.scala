package controllers

import scala.concurrent._
import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import models.{Language, LanguagePair, WordPair}
import models.{LanguageRepository, LanguagePairRepository, WordPairRepository}

class WordPairs @Inject() (
  languageRepository: LanguageRepository,
  languagePairRepository: LanguagePairRepository,
  wordPairRepository: WordPairRepository
) extends Controller {

  def list(fromLangCode: String, toLangCode: String) = Action.async {
    val fromLang = languageRepository.findByCode(fromLangCode)
    val toLang = languageRepository.findByCode(toLangCode)

    val langs = for {
      fl <- fromLang
      tl <- toLang
    } yield (fl, tl)

    val languagePair: Future[Option[LanguagePair]] = langs.flatMap {
      case (Some(fl), Some(tl)) => languagePairRepository.findByLanguages(fl, tl)
      case _ => Future(None)
    }

    val wordPairs: Future[Seq[WordPair]] = languagePair.flatMap {
      case Some(lp) => wordPairRepository.list(lp)
      case _ => Future(Seq[WordPair]())
    }

    // TODO view word pairs
    Future(Ok("Hello."))
  }
}
