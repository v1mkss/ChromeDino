Global / lintUnusedKeysOnLoad := false

ThisBuild / scalaVersion := "3.5.1"
ThisBuild / version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(GraalVMNativeImagePlugin)
  .settings(
    name := "DinoChrome",
    idePackagePrefix := Some("dev.v1mkss.chrome.dino"),
    libraryDependencies += "org.scalafx" %% "scalafx" % "22.0.0-R33",
 //   graalVMNativeImageCommand := "REPLACE_ME",

  )

addCommandAlias("buildNative", "GraalVMNativeImage/packageBin")
