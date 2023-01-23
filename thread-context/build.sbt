libraryDependencies ++= Seq(
  "com.lucidchart" % "thread-context" % "0.7",
  "io.opentracing" % "opentracing-api" % "0.32.0",
  "io.opentracing" % "opentracing-noop" % "0.32.0",
  "io.opentracing" % "opentracing-util" % "0.32.0"
)

name := s"opentracing-${name.value}"
