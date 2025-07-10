pluginManagement {
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
        maven(url = "https://repository.map.naver.com/archive/maven")
        maven(url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/"))
    }
}

rootProject.name = "Saegil-Android"
include(":app")
include(":presentation")
include(":data")
include(":presentation:map")
include(":presentation:learning")
include(":presentation:mypage")
include(":presentation:notice")
include(":core")
include(":core:designsystem")
include(":domain")
include(":presentation:onboarding")
include(":presentation:splash")
include(":core:ui")
include(":presentation:log")
include(":core:common")
include(":presentation:news")
