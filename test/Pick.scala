package test

import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification._

import play.api.test._
import play.api.test.Helpers._

import models._
import test.setup._

class PickSpec extends Specification with BeforeEach with Inject {
  sequential

  val languageRepository = inject[LanguageRepository]
  val languagePairRepository = inject[LanguagePairRepository]

  def before() = {
    Fixtures.languages.map(_ => languageRepository.insert(_))
    Fixtures.languagePairs.map(_ => languagePairRepository.insert(_))
  }

  "the homepage" should {
    "list the language pairs" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentAsString(home) must contain ("cs-CZ")
    }
  }
}
