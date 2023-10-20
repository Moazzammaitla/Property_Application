plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.devtools.ksp")


}

android {
    namespace = "com.example.signup_project"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.signup_project"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
    viewBinding {
        enable = true
    }




}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
//    implementation("androidx.databinding:adapters:3.2.0-alpha11")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    //adding dependency
//    def room_version = "2.5.2"
//
//    implementation("androidx.room:room-runtime:$room_version")
//    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$room_version")


    // Room components
//    implementation ("androidx.room:room-runtime:2.3.0")
//    annotationProcessor ("androidx.room:room-compiler:2.3.0")

// Lifecycle components
//    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.3.1")
//    kapt ("androidx.lifecycle:lifecycle-compiler:2.3.1")

//    KSP ("androidx.room:room-compiler:2.5.2")
//    implementation ("androidx.room:room-ktx:2.5.2")
   implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")






    ksp("androidx.room:room-compiler:2.5.2")

    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-runtime:2.5.2")

    implementation("androidx.room:room-ktx:2.5.2")











}