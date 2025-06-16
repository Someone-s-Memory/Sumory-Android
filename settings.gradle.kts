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
include(":core:design-system")
include(":core:model")
include(":core:ui")
include(":core:domain")
include(":core:data")

include(":feature")
include(":feature:home")
include(":feature:calendar")
include(":feature:diary")
include(":feature:stat")
include(":feature:profile")
include(":feature:store")
include(":feature:setting")
