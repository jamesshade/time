organization := "org.shade"

name := "time"

version := "1.1.0-SNAPSHOT"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.11.6", "2.10.5")

scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation")

resolvers ++= Seq(
  Resolver.mavenLocal,
  Opts.resolver.sonatypeReleases,
  Opts.resolver.sonatypeSnapshots
)

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.2" withSources() withJavadoc(),
  "org.joda" % "joda-convert" % "1.3.1" withSources() withJavadoc(),
  //
  "org.scalatest" %% "scalatest" % "2.2.4" % "test" withSources() withJavadoc(),
  "org.mockito" % "mockito-all" % "1.9.0" % "test" withSources() withJavadoc()
)

import bintray.Keys._

bintraySettings ++ Seq(
  bintrayOrganization in bintray := Some("jamesshade"),
  repository in bintray := "releases"
)


licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
