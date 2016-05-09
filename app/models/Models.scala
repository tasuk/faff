package models

case class User(
  id: Option[Int] = None,
  name: String,
  email: String)

case class Language(
  id: Option[Int] = None,
  code: String,
  name: String)

case class LanguagePair(
  id: Option[Int] = None,
  fromLanguage: Language,
  toLanguage: Language,
  maintainers: List[User] = List[User]())

case class WordPair(
  id: Option[Int] = None,
  fromWord: String,
  toWord: String,
  languagePair: LanguagePair,
  createdBy: Option[User] = None,
  approved: Boolean = true)
