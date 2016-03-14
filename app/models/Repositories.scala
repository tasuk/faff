package models

import scala.concurrent.Future

trait LanguageRepository {
  def insert(language: Language): Future[Int]
  def list: Future[Seq[Language]]
}
