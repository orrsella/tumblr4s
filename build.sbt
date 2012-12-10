name := "tumblr4s"

version := "1.0"

organization := "tumblr4s"

scalaVersion := "2.9.2"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
	"net.databinder" %% "dispatch-http" % "0.8.8",
  "net.databinder" %% "dispatch-oauth" % "0.8.8",
  "net.databinder" %% "dispatch-mime" % "0.8.8",
  "org.json4s" %% "json4s-native" % "3.0.0",
  "org.scalatest" %% "scalatest" % "1.8" % "test"
)
