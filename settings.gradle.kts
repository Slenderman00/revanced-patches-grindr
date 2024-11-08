include("dummy")

rootProject.name = "revanced-patches-grindr"

buildCache {
    local {
        isEnabled = !System.getenv().containsKey("CI")
    }
}

include(":stub")