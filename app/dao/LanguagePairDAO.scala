package dao

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

class LanguagePairDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
    extends LanguageComponent with HasDatabaseConfigProvider[JdbcProfile] with LanguagePairRepository {

  import driver.api._

  private val languages = TableQuery[LanguageTable]
  private val languagePairs = TableQuery[LanguagePairTable]

  def insert(languagePair: LanguagePair) =
    db.run(languagePairs += LanguagePairRow(
      id = 0,
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
          toLanguage = Language(code = toLanguageCode, name = toLanguageName),
          maintainers = Option(List[User]())
        )
      }
    )
  }

  class LanguagePairTable(tag: Tag) extends Table[LanguagePairRow](tag, "LanguagePair") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def fromLanguageId = column[Int]("fromLanguageId")
    def toLanguageId = column[Int]("toLanguageId")

    def * = (id, fromLanguageId, toLanguageId) <> (LanguagePairRow.tupled, LanguagePairRow.unapply _)
  }
}