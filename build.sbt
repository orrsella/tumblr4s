name := "tumblr4s"

organization := "com.orrsella"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-http" % "0.8.9",
  "net.databinder" %% "dispatch-oauth" % "0.8.9",
  "net.databinder" %% "dispatch-mime" % "0.8.9",
  "org.json4s" %% "json4s-native" % "3.0.0",
  "org.scalatest" %% "scalatest" % "1.8" % "test")

// publishing related
crossScalaVersions := Seq("2.9.2")

publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>https://github.com/orrsella/tumblr4s</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:orrsella/tumblr4s.git</url>
    <connection>scm:git:git@github.com:orrsella/tumblr4s.git</connection>
  </scm>
  <developers>
    <developer>
      <id>orrsella</id>
      <name>Orr Sella</name>
      <url>http://orrsella.com</url>
    </developer>
  </developers>
)
