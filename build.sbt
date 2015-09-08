import sbt.Keys._

// ··· Project Info ···

name := "play-prerender"

organization := "com.github.jarlakxen"

crossScalaVersions := Seq("2.11.7")

scalaVersion <<= (crossScalaVersions) { versions => versions.head }

fork in run   := true

publishMavenStyle := true

publishArtifact in Test := false

// ··· Project Options ···

javacOptions ++= Seq(
    "-source", "1.7",
    "-target", "1.7"
)

scalacOptions ++= Seq(
    "-encoding",
    "utf8",
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-unchecked",
    "-deprecation"
)

// ··· Project Repositories ···

resolvers ++= Seq("OSS" at "http://oss.sonatype.org/content/repositories/releases/")

// ··· Project Dependancies···

libraryDependencies ++= Seq(
  // --- Play ---
  "com.typesafe.play"             %% "play"               % "2.3.10"   %  "provided",
  "com.typesafe.play"             %% "play-ws"            % "2.3.10"   %  "provided"
)

pomExtra := (
  <url>https://github.com/Jarlakxen/play-prerender</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/Jarlakxen/play-prerender</url>
    <connection>scm:git:git@github.com:Jarlakxen/play-prerender.git</connection>
    <developerConnection>scm:git:git@github.com:Jarlakxen/play-prerender.git</developerConnection>
  </scm>
  <developers>
    <developer>
      <id>Jarlakxen</id>
      <name>Facundo Viale</name>
      <url>https://github.com/Jarlakxen/play-prerender</url>
    </developer>
  </developers>
)

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.endsWith("-SNAPSHOT"))
  Some("sonatype-nexus-snapshots" at nexus + "content/repositories/snapshots/")
  else
  Some("sonatype-nexus-staging" at nexus + "service/local/staging/deploy/maven2/")
}
