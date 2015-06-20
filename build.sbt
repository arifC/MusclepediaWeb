import play.PlayJava

name := """play-java"""

version := "1.0-SNAPSHOT"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  javaEbean,
  cache,
  ws,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.play" %% "play-mailer" % "2.4.1"
  //"org.xerial" % "sqlite-jdbc" % "3.7.15-M1"
)

fork in run := true


fork in run := true