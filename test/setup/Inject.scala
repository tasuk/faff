package test.setup

import scala.reflect.ClassTag

import play.api.inject.guice.GuiceApplicationBuilder

trait Inject {
  lazy val injector = (new GuiceApplicationBuilder).injector()

  def inject[T: ClassTag]: T = injector.instanceOf[T]
}
