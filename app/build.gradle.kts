plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.easychat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easychat"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // UI and AndroidX dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Firebase BOM for version consistency
    implementation(platform(libs.firebase.bom))

    // Firebase SDKs (without explicit versions)
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.firestore.ktx)
    implementation(libs.google.firebase.storage.ktx)
    implementation(libs.google.firebase.messaging.ktx)

    // Firebase App Check
    implementation(libs.google.firebase.appcheck)
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-appcheck-debug")

    // FirebaseUI for Firestore
    implementation(libs.firebase.ui.firestore)

    // Additional Libraries
    implementation(libs.countryCodePicker)
    implementation(libs.imagepicker)
    implementation(libs.glide)
    implementation(libs.okhttp)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// Apply Google Services plugin at the end
apply(plugin = "com.google.gms.google-services")
