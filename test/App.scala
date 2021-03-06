package test

import org.specs2.mutable._
import org.specs2.runner._
import org.specs2.specification._

import play.api.test._
import play.api.test.Helpers._

import models._
import test.setup._

class AppSpec extends Specification with BeforeEach with Fixtures {
  sequential

  "the homepage" should {
    "list the language pairs" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentAsString(home) must contain ("cs")
    }
  }

  "the word list" should {
    "list the words" in new WithApplication {
      val words = route(FakeRequest(GET, "/pl-cs")).get

      status(words) must equalTo(OK)
      contentAsString(words) must contain ("Chytrý telefon")
      contentAsString(words) must contain ("Porouchaný")
    }
  }
}
