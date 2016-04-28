package models

import scala.concurrent.Future

trait LanguageRepository {
  def insert(language: Language): Future[Language]
  def findByCode(code: String): Future[Option[Language]]
  def list: Future[Seq[Language]]
  def delete: Future[Int]
}

trait LanguagePairRepository {
  def insert(languagePair: LanguagePair): Future[Int]
  def list: Future[Seq[LanguagePair]]
  def delete: Future[Int]
}
