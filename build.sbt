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
  "org.clapper" %% "grizzled-slf4j" % "1.0.1" withSources() withJavadoc(),
  //
  "joda-time" % "joda-time" % "2.2" withSources() withJavadoc(),
  "org.joda" % "joda-convert" % "1.3.1" withSources() withJavadoc(),
  //
  "junit" % "junit" % "4.10" % "test",
  "org.scalatest" %% "scalatest" % "2.0.RC1" % "test" withSources() withJavadoc(),
  "org.mockito" % "mockito-all" % "1.9.0" % "test" withSources() withJavadoc()
)

