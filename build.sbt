import play.PlayJava

name := """play-java"""

version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  javaEbean,
  cache,
  ws,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.apache.commons" % "commons-email" % "1.4"
 // "it.innove" % "play2-pdf" % "1.2.0"
  //"org.xerial" % "sqlite-jdbc" % "3.7.15-M1"
)


fork in run := true


fork in run := true

fork in run := true

fork in run := true

fork in run := true

fork in run := true

fork in run := true

fork in run := true

fork in run := true