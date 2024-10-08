plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.sixtyninefourtwenty.bottomsheetalertdialog"
    compileSdk = 34

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        compileOptions {
            freeCompilerArgs += "-Xjvm-default=all"
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.sixtyninefourtwenty"
                artifactId = "bottom-sheet-alert-dialog"
                version = "2.0.2"

                from(components["release"])

                pom {
                    name.set("bottom-sheet-alert-dialog")
                    description.set("Create bottom sheets with title and buttons")
                    url.set("https://github.com/unbiaseduser-github/bottom-sheet-alert-dialog")

                    licenses {
                        license {
                            name.set("The Apache Software License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("unbiaseduser")
                            name.set("Dang Quang Trung")
                            email.set("quangtrung02hn16@gmail.com")
                            url.set("https://github.com/unbiaseduser-github")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/unbiaseduser-github/bottom-sheet-alert-dialog.git")
                        developerConnection.set("scm:git:ssh://github.com:unbiaseduser-github/bottom-sheet-alert-dialog.git")
                        url.set("https://github.com/unbiaseduser-github/bottom-sheet-alert-dialog/tree/master")
                    }
                }
            }
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}