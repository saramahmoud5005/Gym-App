import com.android.build.gradle.internal.manifest.ManifestData
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.sonarqube")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.gymapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gymapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    task("increaseVersionInformation"){
//        doLast {
//            android.defaultConfig.versionCode = ant.project.getProperty("VERSION_CODE") as Int
//            ant.project.setProperty(ant.project.getProperty("VERSION_CODE"), (ant.project.getProperty("VERSION_CODE") as Int?)?.plus(1).toString() )
//        }
        val currentVersionCode = defaultConfig.versionCode
        if (currentVersionCode != null) {
            defaultConfig.versionCode = currentVersionCode + 1
        }



        // Update the AndroidManifest.xml file with the new version code
        val manifestFile = file("${projectDir}/src/main/AndroidManifest.xml")
        if (manifestFile.exists()) {
            val manifestContent = manifestFile.readText()
            val updatedManifestContent = manifestContent.replace(
                "android:versionCode=\"${currentVersionCode}\"",
                "android:versionCode=\"${currentVersionCode?.plus(1)}\""
            )
            manifestFile.writeText(updatedManifestContent)
        } else {
            logger.error("AndroidManifest.xml not found.")
        }

//            android.defaultConfig.versionCode = android.defaultConfig.versionCode?.plus(1)
//        doLast {
//            applicationVariants.all { variant ->
//                val versionCode = variant.mergedFlavor.versionCode?.plus(1)
//                code = variant.versionCode.plus(1)
//                android.defaultConfig.versionCode = variant.versionCode.plus(1)
//                println("Version Code: $versionCode")
//                true
//            }
//        }

//            println("v "+android.defaultConfig.versionCode+"("+android.defaultConfig.versionName+")")


//        var buildFile = file("build.gradle.kts")
//        if(android.defaultConfig.versionCode==null) android.defaultConfig.versionCode = 1

    //    var index = 1
//    if(android.defaultConfig.versionName?.get(1)?.toInt()==9) index = index.plus(2)
//    var versionName = android.defaultConfig.versionName?.get(index)?.toInt()
//    versionName = versionName?.plus(1)
//    android.defaultConfig.versionName = versionName.toString()
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.0")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}
kapt {
    correctErrorTypes = true
}

task("printVersionInformation"){

    println("v"+android.defaultConfig.versionCode+"("+android.defaultConfig.versionName+")")
}
tasks.named("printVersionInformation") { dependsOn("increaseVersionInformation") }


