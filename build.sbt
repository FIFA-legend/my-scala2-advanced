name := "my-scala2-advanced2"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
)
