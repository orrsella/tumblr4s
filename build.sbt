name := "tumblr4s"

version := "1.1.0"

organization := "com.orrsella"

scalaVersion := "2.9.2"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-http" % "0.8.9",
  "net.databinder" %% "dispatch-oauth" % "0.8.9",
  "net.databinder" %% "dispatch-mime" % "0.8.9",
  "org.json4s" %% "json4s-native" % "3.0.0",
  "org.scalatest" %% "scalatest" % "1.8" % "test"
)
