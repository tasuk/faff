package dao

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import models.{WordPair, WordPairRepository}
import models.{LanguagePair, User}

case class WordPairRow(
  id: Int,
  fromWord: String,
  toWord: String,
  languagePairId: Int)

class WordPairDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
    extends HasDatabaseConfigProvider[JdbcProfile] with WordPairRepository {

  import driver.api._

  class WordPairTable(tag: Tag) extends Table[WordPairRow](tag, "LanguagePair") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def fromWord = column[String]("fromWord")
    def toWord = column[String]("toWord")
    def languagePairId = column[Int]("languagePairId")

    def * = (id, fromWord, toWord, languagePairId) <> (WordPairRow.tupled, WordPairRow.unapply _)
  }

  val wordPairs = TableQuery[WordPairTable]

  def insert(wordPair: WordPair) =
    db.run(wordPairs += WordPairRow(
      id = wordPair.id.getOrElse(0),
      fromWord = wordPair.fromWord,
      toWord = wordPair.toWord,
      languagePairId = wordPair.languagePair.id.getOrElse(0)))

  def list(languagePair: LanguagePair) = {
    val query = wordPairs.filter(_.languagePairId === languagePair.id)
    db.run(query.result).map(
      _.map {
        row => WordPair(Option(row.id), row.fromWord, row.toWord, languagePair)
      }
    )
  }

  def delete =
    db.run(wordPairs.delete)
}
