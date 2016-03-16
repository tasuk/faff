package controllers

import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import models.Language
import models.LanguageRepository

class WordPair @Inject() (languageRepository: LanguageRepository) extends Controller {
  def index = Action.async {
    languageRepository.list.map { languages =>
      Ok(views.html.home(languages))
    }
  }

  def insert = Action.async {
    val lang = Language(code="cs-cz", name="Czech")
    languageRepository.insert(lang).map { _ =>
      Ok("Inserted a language")
    }
  }
}
