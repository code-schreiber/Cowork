object Versions {
    const val gradle = "3.1.4"
    const val kotlin = "1.2.51"

    const val versionCode = 1
    const val versionName = "0.0.1"

    const val minSdkVersion = 16
    const val targetSdkVersion = 28
    const val compileSdkVersion = 28

    // Libraries
    const val googleSupport = "27.1.1"
    const val constraintLayout = "1.1.1"
    const val googleMaps = "15.0.1"

    const val timber = "4.7.0"

    const val dagger = "2.15"

    const val retrofit = "2.4.0"
    const val moshi = "1.6.0"
    const val converterMoshi = "2.4.0"
    const val loggingInterceptor = "3.6.0"

    const val rxAndroid = "2.0.2"

    // Tests
    const val mockitoKotlin = "1.5.0"
    const val kluent = "1.35"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    private const val androidSupport = "com.android.support"
    const val appCompat = "$androidSupport:appcompat-v7:${Versions.googleSupport}"
    const val recyclerView = "$androidSupport:recyclerview-v7:${Versions.googleSupport}"
    const val cardView = "$androidSupport:cardview-v7:${Versions.googleSupport}"
    const val constraintLayout = "$androidSupport.constraint:constraint-layout:${Versions.constraintLayout}"
    const val googleMaps = "com.google.android.gms:play-services-maps:${Versions.googleMaps}"
    const val googleMapsExclude = androidSupport

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.converterMoshi}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    // Tests
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin-kt1.1:${Versions.mockitoKotlin}"
    const val kluent = "org.amshove.kluent:kluent-android:${Versions.kluent}"
}

object Plugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Projects {
    const val app = ":app"
    const val baseMvp = ":base_mvp"
    const val baseRepository = ":base_repository"
    const val baseNetwork = ":base_network"
}
