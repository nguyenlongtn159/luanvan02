name := """warehouse"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies += javaEbean

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
  cache,
  javaWs,
  filters
)