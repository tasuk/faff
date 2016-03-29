package controllers

import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import models.Language
import models.LanguageRepository
import models.LanguagePairRepository

class Pick @Inject() (
    languageRepository: LanguageRepository,
    languagePairRepository: LanguagePairRepository)
    extends Controller {

  def language = Action.async {
    languagePairRepository.list.map { languagePairs =>
      Ok(views.html.home(languagePairs))
    }
  }

  def languagePair(language: String) = Action.async {
    languagePairRepository.list.map { languagePairs =>
      Ok(views.html.home(languagePairs))
    }
  }

  def insert = Action.async {
    val lang = Language(code="cs-cz", name="Czech")
    languageRepository.insert(lang).map { _ =>
      Ok("Inserted a language")
    }
  }
}
