pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
enableFeaturePreview("VERSION_CATALOGS")
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
include(":feature:home")
include(":core:domain")
include(":feature:keepdetail")
include(":feature:openlicense")
