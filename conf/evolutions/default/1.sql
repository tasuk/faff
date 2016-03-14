# --- !Ups

CREATE TABLE User (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX email_UNIQUE (email),
  UNIQUE INDEX name_UNIQUE (name))
ENGINE = InnoDB;

CREATE TABLE Language (
  id INT NOT NULL AUTO_INCREMENT,
  code VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX code_UNIQUE (code),
  UNIQUE INDEX name_UNIQUE (name))
ENGINE = InnoDB;

CREATE TABLE LanguagePair (
  id INT NOT NULL,
  fromLanguageId INT NOT NULL,
  toLanguageId INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_fromLanguage (fromLanguageId),
  INDEX fk_toLanguage (toLanguageId),
  UNIQUE INDEX fromToLanguage (fromLanguageId, toLanguageId),
  CONSTRAINT fk_LanguagePair_fromLanguage
    FOREIGN KEY (fromLanguageId)
    REFERENCES Language (id),
  CONSTRAINT fk_LanguagePair_toLanguage
    FOREIGN KEY (toLanguageId)
    REFERENCES Language (id))
ENGINE = InnoDB;

CREATE TABLE LanguagePairMaintainer (
  languagePairId INT NOT NULL,
  userId INT NOT NULL,
  PRIMARY KEY (languagePairId, userId),
  INDEX fk_LanguagePairMaintainer_User (userId ASC),
  CONSTRAINT fk_LanguagePairMaintainer_User
    FOREIGN KEY (userId)
    REFERENCES User (id),
  CONSTRAINT fk_LanguagePairMaintainer_LanguagePair
    FOREIGN KEY (languagePairId)
    REFERENCES LanguagePair (id))
ENGINE = InnoDB;

CREATE TABLE WordPair (
  id INT NOT NULL,
  languagePair INT NOT NULL,
  fromWord VARCHAR(255) NOT NULL,
  toWord VARCHAR(255) NOT NULL,
  author INT NULL,
  approved TINYINT(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX fromWord_UNIQUE (fromWord),
  INDEX fk_WordPair_LanguagePair (languagePair),
  INDEX fk_WordPair_User (author),
  CONSTRAINT fk_WordPair_LanguagePair
    FOREIGN KEY (languagePair)
    REFERENCES LanguagePair (id),
  CONSTRAINT fk_WordPair_User
    FOREIGN KEY (author)
    REFERENCES User (id))
ENGINE = InnoDB;

# --- !Downs

DROP TABLE User;
DROP TABLE Language;
DROP TABLE LanguagePair;
DROP TABLE LanguagePairMaintainer;
DROP TABLE WordPair;
