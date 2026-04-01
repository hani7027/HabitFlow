import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    androidLibrary {
        namespace = "com.hk.habitflow.design"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Design"
            isStatic = true
            binaryOption("bundleId", "com.hk.habitflow.Design")
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation("org.jetbrains.compose.material3:material3:${libs.versions.material3.get()}") {
                exclude(group = "org.jetbrains.compose.material", module = "material-icons-extended")
            }
            implementation(libs.compose.ui)
        }
        androidMain.dependencies {
            implementation("org.jetbrains.compose.material3:material3:${libs.versions.material3.get()}") {
                exclude(group = "org.jetbrains.compose.material", module = "material-icons-extended")
            }
            implementation(libs.androidx.core.ktx)
        }
        iosMain.dependencies {
            implementation("org.jetbrains.compose.material3:material3:${libs.versions.material3.get()}") {
                exclude(group = "org.jetbrains.compose.material", module = "material-icons-extended")
            }
        }
    }
}
