package dao

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

import models.Language
import models.LanguageRepository

class LanguageDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
    extends HasDatabaseConfigProvider[JdbcProfile] with LanguageRepository {

  import driver.api._

  private val languages = TableQuery[LanguageTable]

  def insert(language: Language) =
    db.run(languages += language)

  def list =
    db.run(languages.result)

  class LanguageTable(tag: Tag) extends Table[Language](tag, "Language") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def code = column[String]("code")
    def name = column[String]("name")

    def * = (id.?, code, name) <> (Language.tupled, Language.unapply _)
  }
}
