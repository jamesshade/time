name := "time"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.10.2"

scalacOptions ++= Seq("-deprecation")

resolvers ++= Seq(
  Resolver.mavenLocal,
  Opts.resolver.sonatypeReleases,
  Opts.resolver.sonatypeSnapshots
)

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.2" withSources() withJavadoc(),
  "org.joda" % "joda-convert" % "1.3.1" withSources() withJavadoc(),
  //
  "org.scalatest" %% "scalatest" % "2.0.RC2" % "test" withSources() withJavadoc(),
  "org.mockito" % "mockito-all" % "1.9.0" % "test" withSources() withJavadoc()
)

