import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

class PickSpec extends Specification {
  "the homepage" should {
    "list the language pairs" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentAsString(home) must contain ("cs-CZ")
    }
  }
}
