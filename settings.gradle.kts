pluginManagement {
    repositories {
        google()
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

rootProject.name = "Habits App"
include(":app")
include(":core")
include(":core:core_data")
include(":core:core_presentation")
include(":home")
include(":home:home_domain")
include(":home:home_data")
include(":home:home_presentation")
include(":authentication")
include(":authentication:authentication_domain")
include(":authentication:authentication_data")
include(":authentication:authentication_presentation")
include(":onboarding")
include(":onboarding:onboarding_data")
include(":onboarding:onboarding_presentation")
include(":settings")
include(":settings:settings_presentation")
include(":onboarding:onboarding_domain")
