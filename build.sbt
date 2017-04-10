organization := "com.jonathan"
name := "scala-akka"

version := "1.0"
scalaVersion := "2.12.1"

val v = Map(
  "akka-actor"            -> "2.4.17",
  "couchbase-java-client" -> "2.4.4",
  "joda-time"             -> "2.9.9",
  "lift-json"             -> "3.0.1",
  "logback"               -> "1.1.7",
  "scala-logging"         -> "3.5.0",
  "scala-test"            -> "3.0.1"
)

libraryDependencies += "ch.qos.logback"              % "logback-classic"  % v("logback")                          withSources() withJavadoc()
libraryDependencies += "com.couchbase.client"        % "java-client"      % v("couchbase-java-client")            withSources() withJavadoc()
libraryDependencies += "com.typesafe.akka"          %% "akka-actor"       % v("akka-actor")                       withSources() withJavadoc()
libraryDependencies += "com.typesafe.akka"          %% "akka-testkit"     % v("akka-actor")              % "test" withSources() withJavadoc()
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"    % v("scala-logging")                    withSources() withJavadoc()
libraryDependencies += "joda-time"                   % "joda-time"        % v("joda-time")                        withSources() withJavadoc()
libraryDependencies += "net.liftweb"                %% "lift-json"        % v("lift-json")                        withSources() withJavadoc()
libraryDependencies += "org.scalactic"              %% "scalactic"        % v("scala-test")                       withSources() withJavadoc()
libraryDependencies += "org.scalatest"              %% "scalatest"        % v("scala-test")              % "test" withSources() withJavadoc()
