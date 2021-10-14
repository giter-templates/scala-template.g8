import com.typesafe.sbt.packager.docker._
import Dependencies._

lazy val compilerOptions: Seq[String] = Seq(
  "-deprecation",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-explaintypes",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:existentials",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xlint:constant",
  "-Xlint:inaccessible",
  "-Xlint:nullary-unit",
  "-Xlint:type-parameter-shadow",
  "-Xlint:_,-byname-implicit",
  "-Ymacro-annotations",
  "-Wdead-code",
  "-Wnumeric-widen",
  "-Wunused:explicits",
  "-Wunused:implicits",
  "-Wunused:imports",
  "-Wunused:locals",
  "-Wunused:patvars",
  "-Wunused:privates",
  "-Wvalue-discard",
  "-Xlint:deprecation",
  "-Xlint:eta-sam",
  "-Xlint:eta-zero",
  "-Xlint:implicit-not-found",
  "-Xlint:infer-any",
  "-Xlint:nonlocal-return",
  "-Xlint:unused",
  "-Xlint:valpattern"
)

lazy val root =
  project
    .in(file("."))
    .enablePlugins(JavaAppPackaging, DockerPlugin)
    .settings(
      name := "$name$",
      version := "0.1",
      scalaVersion := "2.13.6",
      scalacOptions ++= compilerOptions,
      libraryDependencies ++= Seq(scalaTest % Test),
      addCompilerPlugin(
        ("org.typelevel" %% "kind-projector" % kindProjectorVersion)
          .cross(CrossVersion.full)
      ),
      Test / parallelExecution := false
    )
    .settings(
      dockerBaseImage := "openjdk:17-alpine",
      dockerRepository := Some("index.docker.io"),
      dockerUpdateLatest := true,
      Docker / packageName := "$name$",
      Docker / version := "latest",
      Docker / daemonUser := "daemon",
      dockerCommands ++= Seq(
        Cmd("USER", "root"),
        Cmd("RUN", "apk", "add", "--no-cache", "bash"),
        Cmd("USER", (Docker / daemonUser).value)
      )
    )
