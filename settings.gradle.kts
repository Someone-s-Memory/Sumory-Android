pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Sumory"
include(":app")

include(":core")
include(":core:design")
include(":core:model")
include(":core:ui")

include(":data")
include(":data:auth")
include(":data:diary")

include(":domain")
include(":domain:auth")
include(":domain:diary")
