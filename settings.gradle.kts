pluginManagement {
    repositories {
        maven { url = uri("https://mvn.ivcode.org/mvn/snapshot") }
        mavenCentral()
        gradlePluginPortal()
    }

}

rootProject.name = "auth"
include("backend")