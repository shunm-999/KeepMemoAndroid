pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "KeepMemo"
includeBuild("build-logic")
include(":app")
include(":core:common")
include(":core:model")
include(":core:database")
include(":core:testing")
include(":core:data")
include(":core:designsystem")
include(":core:navigation")
include(":core:ui")
include(":core:domain")
include(":feature:home")
include(":feature:keepdetail")
include(":feature:openlicense")
