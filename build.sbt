organization := "org.shade"

name := "time"

version := "2.0.0-SNAPSHOT"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.11.6", "2.10.5")

scalacOptions ++= Seq(
  "-unchecked",
  "-feature",
  "-deprecation",
  "-target:jvm-1.8"
)

resolvers ++= Seq(
  Resolver.mavenLocal,
  Opts.resolver.sonatypeReleases,
  Opts.resolver.sonatypeSnapshots
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test" withSources() withJavadoc(),
  "org.mockito" % "mockito-all" % "1.9.0" % "test" withSources() withJavadoc()
)

import bintray.Keys._

bintraySettings ++ Seq(
  bintrayOrganization in bintray := Some("jamesshade"),
  repository in bintray := "releases"
)


licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
