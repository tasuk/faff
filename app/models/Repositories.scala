package models

import scala.concurrent.Future

trait LanguageRepository {
  def insert(language: Language): Future[Int]
  def list: Future[Seq[Language]]
}

trait LanguagePairRepository {
  def insert(languagePair: LanguagePair): Future[Int]
  def list: Future[Seq[LanguagePair]]
}
