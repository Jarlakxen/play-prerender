import sbt.Keys._
import SonatypeKeys._

// ··· Settings ···

sonatypeSettings

// ··· Project Info ···

name := "play-prerender"

organization := "com.github.jarlakxen"

crossScalaVersions := Seq("2.10.4", "2.11.1")

scalaVersion <<= (crossScalaVersions) { versions => versions.head }

fork in run   := true

publishMavenStyle := true

publishArtifact in Test := false


// ··· Project Enviroment ···

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE17)

unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil

unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil


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
  "org.scala-lang"                %  "scala-reflect"      % "2.10.4",
  // --- Play ---
  "com.typesafe.play"             %% "play"               % "2.3.0"   %  "provided",
  "com.typesafe.play"             %% "play-ws"            % "2.3.0"   %  "provided"
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
