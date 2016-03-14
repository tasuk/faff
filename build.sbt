name := "false-and-funny-friends"

version := "0.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.34"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "1.1.1"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.1.0"

autoAPIMappings := true
