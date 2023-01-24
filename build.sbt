lazy val `thread-context` = project

inScope(Global)(Seq(
  autoScalaLibrary := false,
  credentials += Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    sys.env.getOrElse("SONATYPE_USERNAME", ""),
    sys.env.getOrElse("SONATYPE_PASSWORD", "")
  ),
  crossPaths := false,
  developers += Developer("pauldraper", "Paul Draper", "paulddraper@gmail.com", url("https://github.com/pauldraper")),
  homepage := Some(url("https://git.lucidchart.com/lucidsoftware/opentracing-thread-context")),
  licenses += "Apache 2.0 License" -> url("https://www.apache.org/licenses/LICENSE-2.0"),
  organization := "com.lucidchart",
  organizationHomepage := Some(url("https://github.com/lucidsoftware")),
  organizationName := "Lucid Software",
  resolvers += Resolver.typesafeRepo("releases"),
  scmInfo := Some(ScmInfo(
    url("https://github.com/lucidsoftware/opentracing-thread-context"),
    "scm:git:git@github.com:lucidsoftware/opentracing-thread-context"
  )),
  startYear := Some(2017),
  version := sys.props.getOrElse("build.version", "0-SNAPSHOT")
))
skip in publish := true
