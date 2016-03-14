package models

case class User(
  id: Option[Long] = None,
  name: String,
  email: String)

case class Language(
  id: Option[Long] = None,
  code: String,
  name: String)

case class LanguagePair(
  id: Option[Long] = None,
  fromLanguage: Language,
  toLanguage: Language,
  maintainers: List[User])

case class WordPair(
  id: Option[Long] = None,
  fromWord: String,
  toWord: String,
  languagePair: LanguagePair,
  createdBy: User)
