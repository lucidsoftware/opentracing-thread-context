libraryDependencies ++= Seq(
  "com.lucidchart" % "thread-context" % "0.7",
  "io.opentracing" % "opentracing-api" % "0.31.0",
  "io.opentracing" % "opentracing-noop" % "0.31.0"
)

name := s"opentracing-${name.value}"
