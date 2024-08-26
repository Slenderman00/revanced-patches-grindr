import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.binary.compatibility.validator)
    `maven-publish`
    signing
}

group = "patches.grindr"

repositories {
    mavenCentral()
    mavenLocal()
    google()
    maven {
        // A repository must be speficied for some reason. "registry" is a dummy.
        url = uri("https://maven.pkg.github.com/revanced/registry")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation(libs.revanced.patcher)
    implementation(libs.smali)
}

kotlin {
    jvmToolchain(11)
}

tasks {
    withType(Jar::class) {
        manifest {
            attributes["Name"] = "Grindr ReVanced Patches"
            attributes["Description"] = "Grindr Patches template for ReVanced."
            attributes["Version"] = version
            attributes["Timestamp"] = System.currentTimeMillis().toString()
            attributes["Source"] = "git@github.com:lg33/revanced-patches-grindr.git"
            attributes["Author"] = "LG33"
            attributes["Contact"] = "contact@lgaillard.fr"
            attributes["Origin"] = "https://lgaillard.fr"
            attributes["License"] = "GNU General Public License v3.0"
        }
    }

    register("buildDexJar") {
        description = "Build and add a DEX to the JAR file"
        group = "build"

        dependsOn(build)

        doLast {
            val d8 = File(System.getenv("ANDROID_HOME")).resolve("build-tools")
                .listFilesOrdered().last().resolve("d8").absolutePath

            val patchesJar = configurations.archives.get().allArtifacts.files.files.first().absolutePath
            val workingDirectory = layout.buildDirectory.dir("libs").get().asFile

            exec {
                workingDir = workingDirectory
                commandLine = listOf(d8, "--release", patchesJar)
            }

            exec {
                workingDir = workingDirectory
                commandLine = listOf("zip", "-u", patchesJar, "classes.dex")
            }
        }
    }

    // Needed by gradle-semantic-release-plugin.
    // Tracking: https://github.com/KengoTODA/gradle-semantic-release-plugin/issues/435
    publish {
        dependsOn("buildDexJar")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/revanced/revanced-patches-template")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("revanced-patches-publication") {
            from(components["java"])

            pom {
                name = "Grindr ReVanced Patches template"
                description = "Grindr Patches template for ReVanced."
                url = "https://lgaillard.fr"

                licenses {
                    license {
                        name = "GNU General Public License v3.0"
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    }
                }
                developers {
                    developer {
                        id = "LG33"
                        name = "LG33"
                        email = "contact@lgaillard.fr"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/lg33/revanced-patches-grindr.git"
                    developerConnection = "scm:git:git@github.com:lg33/revanced-patches-grindr.git"
                    url = "https://github.com/lg33/revanced-patches-grindr"
                }
            }
        }
    }
}

signing {
    useGpgCmd()

    sign(publishing.publications["revanced-patches-publication"])
}