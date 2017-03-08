libraryDependencies ++= Seq(
  "com.lucidchart" % "thread-context" % "0.7",
  "io.opentracing" % "opentracing-api" % "0.20.7",
  "io.opentracing" % "opentracing-noop" % "0.20.7"
)

name := s"opentracing-${name.value}"
