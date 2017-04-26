organization := "com.jonathan"
name := "scala-akka"
version := "1.0"

scalaVersion := "2.12.1"

val v = Map(
  "akka-actor"            -> "2.4.17",
  "akka-http-core"        -> "10.0.5",
  "couchbase-java-client" -> "2.4.4",
  "jackson"               -> "2.8.8",
  "joda-time"             -> "2.9.9",
  "logback"               -> "1.1.7",
  "mongodb"               -> "2.0.0",
  "scala-logging"         -> "3.5.0",
  "scala-test"            -> "3.0.1"
)

libraryDependencies += "ch.qos.logback"                % "logback-classic"      % v("logback")                          withSources() withJavadoc()
libraryDependencies += "com.couchbase.client"          % "java-client"          % v("couchbase-java-client")            withSources() withJavadoc()
libraryDependencies += "com.fasterxml.jackson.core"    % "jackson-databind"     % v("jackson")                          withSources() withJavadoc()
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % v("jackson")                          withSources() withJavadoc()
libraryDependencies += "com.typesafe.akka"            %% "akka-actor"           % v("akka-actor")                       withSources() withJavadoc()
libraryDependencies += "com.typesafe.akka"            %% "akka-http-core"       % v("akka-http-core")                   withSources() withJavadoc()
libraryDependencies += "com.typesafe.scala-logging"   %% "scala-logging"        % v("scala-logging")                    withSources() withJavadoc()
libraryDependencies += "joda-time"                     % "joda-time"            % v("joda-time")                        withSources() withJavadoc()
libraryDependencies += "org.mongodb.scala"            %% "mongo-scala-driver"   % v("mongodb")                          withSources() withJavadoc()

libraryDependencies += "com.typesafe.akka"            %% "akka-testkit"         % v("akka-actor")              % "test" withSources() withJavadoc()
libraryDependencies += "org.scalactic"                %% "scalactic"            % v("scala-test")              % "test" withSources() withJavadoc()
libraryDependencies += "org.scalatest"                %% "scalatest"            % v("scala-test")              % "test" withSources() withJavadoc()

enablePlugins(JavaServerAppPackaging)

mainClass in Compile := Some("com.trakinvest.MainKernel")