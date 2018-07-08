object Versions {
    val gradle = "3.1.3"
    val kotlin = "1.2.51"

    val versionCode = 1
    val versionName = "0.0.1"

    val minSdkVersion = 26
    val targetSdkVersion = 28
    val compileSdkVersion = 28

    // Libraries
    val googleSupport = "27.1.1"
    val constraintLayout = "1.1.1"

    val timber = "4.7.0"

    val dagger = "2.15"

    // Tests
    val mockitoKotlin = "1.5.0"
    val kluent = "1.35"
}

object Dependencies {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    val appCompat = "com.android.support:appcompat-v7:${Versions.googleSupport}"
    val recyclerView = "com.android.support:recyclerview-v7:${Versions.googleSupport}"
    val cardView = "com.android.support:cardview-v7:${Versions.googleSupport}"
    val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"

    // Tests
    val mockitoKotlin = "com.nhaarman:mockito-kotlin-kt1.1:${Versions.mockitoKotlin}"
    val kluent = "org.amshove.kluent:kluent-android:${Versions.kluent}"
}

object Plugins {
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}
