package dao

import scala.concurrent._
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import models.{LanguagePair, LanguagePairRepository}
import models.{Language, User}

case class LanguagePairRow(
  id: Int,
  fromLanguageId: Int,
  toLanguageId: Int)

trait LanguagePairComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  class LanguagePairTable(tag: Tag) extends Table[LanguagePairRow](tag, "LanguagePair") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def fromLanguageId = column[Int]("fromLanguageId")
    def toLanguageId = column[Int]("toLanguageId")

    def * = (id, fromLanguageId, toLanguageId) <> (LanguagePairRow.tupled, LanguagePairRow.unapply _)
  }

  val languagePairs = TableQuery[LanguagePairTable]
}

class LanguagePairDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
    extends LanguageComponent with LanguagePairComponent with HasDatabaseConfigProvider[JdbcProfile] with LanguagePairRepository {

  import driver.api._

  def insert(languagePair: LanguagePair) =
    db.run(languagePairs += LanguagePairRow(
      id = languagePair.id.getOrElse(0),
      fromLanguageId = languagePair.fromLanguage.id.getOrElse(0),
      toLanguageId = languagePair.toLanguage.id.getOrElse(0)))

  def list = {
    val query = for {
      lp <- languagePairs
      fl <- languages if fl.id === lp.fromLanguageId
      tl <- languages if tl.id === lp.toLanguageId
    } yield (lp.id, fl.code, fl.name, tl.code, tl.name)

    db.run(query.result).map(
      rows => rows.map {
        case (id, fromLanguageCode, fromLanguageName, toLanguageCode, toLanguageName) => LanguagePair(
          id = Option(id),
          fromLanguage = Language(code = fromLanguageCode, name = fromLanguageName),
          toLanguage = Language(code = toLanguageCode, name = toLanguageName)
        )
      }
    )
  }

  def findByLanguages(fromLang: Language, toLang: Language) = {
    val query = languagePairs.filter(_.fromLanguageId === fromLang.id)

    db.run(query.result.headOption).flatMap {
      case Some(row) => Future(Option(LanguagePair(Option(row.id), fromLang, toLang)))
      case None => Future(None)
    }
  }

  def delete =
    db.run(languagePairs.delete)
}
