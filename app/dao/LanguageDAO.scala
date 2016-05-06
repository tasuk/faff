package dao

import scala.concurrent._
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import models.{Language, LanguageRepository}

case class LanguageRow(
  id: Int,
  code: String,
  name: String)

trait LanguageComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  class LanguageTable(tag: Tag) extends Table[LanguageRow](tag, "Language") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def code = column[String]("code")
    def name = column[String]("name")

    def * = (id, code, name) <> (LanguageRow.tupled, LanguageRow.unapply _)
  }

  val languages = TableQuery[LanguageTable]
}

class LanguageDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
    extends LanguageComponent with HasDatabaseConfigProvider[JdbcProfile] with LanguageRepository {

  import driver.api._

  def insert(language: Language) = {
    val query = (languages returning languages.map(_.id) into (
      (lang, id) => Language(Option(id), lang.code, lang.name)
    ))

    val insertedLang = query += LanguageRow(
      id = 0,
      code = language.code,
      name = language.name)
    db.run(insertedLang)
  }

  def list =
    db.run(languages.result).map(
      _.map {
        row => Language(Option(row.id), row.code, row.name)
      }
    )

  def findByCode(code: String) = {
    val query = languages.filter(_.code === code)

    db.run(query.result.headOption).flatMap {
      case Some(row) => Future(Option(Language(Option(row.id), row.code, row.name)))
      case None => Future(None)
    }
  }

  def delete =
    db.run(languages.delete)
}
