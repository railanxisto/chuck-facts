object Versions {
    const val kotlin_version = "1.3.21"
    const val support = "28.0.0"
    const val appcompat_version = "1.1.0-alpha03"
    const val constraintlayout_version = "1.1.3"
    const val lifecycle_version = "2.1.0-alpha03"
    const val core_ktx_version = "1.1.0-alpha05"
    const val legacy = "1.0.0"
    const val junit = "4.12"
    const val junitTest = "1.1.0"
    const val retrofit = "2.5.0"
    const val logging_interceptor_version = "3.9.0"
    const val koin = "2.0.0-rc-2"
    const val rxjava_version = "2.2.8"
    const val rxandroid_version = "2.1.1"
    const val flexbox = "1.1.0"
    const val room = "2.1.0-alpha06"
    const val ktlint_version = "0.29.0"
    const val mockito = "2.25.1"
    const val roboletric = "4.2.1"
    const val core_testing = "2.0.1"
    const val androidx_espresso = "3.2.0-alpha02"
    const val test_runner = "1.1.2-alpha02"
}

object Dependencies {
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"

    val compat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    val supportCompat = "com.android.support:appcompat-v7:${Versions.support}"
    val supportDesign = "com.android.support:design:${Versions.support}"
    val coreKtx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout_version}"
    val legacy = "androidx.legacy:legacy-support-v4:${Versions.legacy}"
    val junit = "junit:junit:${Versions.junit}"

    // Api & Http
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor_version}"
    val retrofitRxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    // Koin
    val koin = "org.koin:koin-android:${Versions.koin}"
    val koinViewmodel = "org.koin:koin-android-viewmodel:${Versions.koin}"

    // rxjava
    val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava_version}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid_version}"

    // flexbox
    val flexbox = "com.google.android:flexbox:${Versions.flexbox}"

    // room
    val room = "androidx.room:room-runtime:${Versions.room}"
    val kaptRoom = "androidx.room:room-compiler:${Versions.room}"
    val roomRxjava = "androidx.room:room-rxjava2:${Versions.room}"

    // ktlint
    val ktlint = "com.github.shyiko:ktlint:${Versions.ktlint_version}"

    // mockito
    val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"

    // tests
    val roboletric = "org.robolectric:robolectric:${Versions.roboletric}"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.core_testing}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.androidx_espresso}"
    val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.androidx_espresso}"

    val junitExt = "androidx.test.ext:junit:${Versions.junitTest}"
    val testRunner = "androidx.test:runner:${Versions.test_runner}"
    val koinTest = "org.koin:koin-test:${Versions.koin}"
}
