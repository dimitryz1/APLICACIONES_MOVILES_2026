plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.avance_t1"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.avance_t1"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Usar rootProject para acceder a las propiedades de forma más limpia en Kotlin DSL
        val mapsApiKey = project.rootProject.extra.properties["MAPS_API_KEY"] as? String 
                        ?: (project.findProperty("MAPS_API_KEY") as? String) ?: ""

        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    // Room dependencies
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Google Play Services
    implementation(libs.play.services.auth)
    implementation(libs.play.services.maps)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
