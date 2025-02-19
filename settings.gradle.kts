pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/") // ✅ 카카오 저장소 추가
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // ✅ 프로젝트 단위가 아닌 settings.gradle.kts 우선 적용
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/") // ✅ 카카오 저장소 추가
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/") // 카카오 지도 SDK 저장소
    }
}


rootProject.name = "GymBuddy_back"
include(":app")
