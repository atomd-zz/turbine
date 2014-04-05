name := "turbine"

version := "1.0"

organization := "me.atomd"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.1",
  "com.scalarx" % "scalarx_2.10" % "0.2.3",
  "org.openflow" % "openflowj" % "1.0.3" from "https://openflow.stanford.edu/static/openflowj/releases/1.0.3/openflowj-1.0.3.jar"
)
