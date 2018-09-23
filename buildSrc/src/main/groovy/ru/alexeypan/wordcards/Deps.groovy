package ru.alexeypan.wordcards

class ApplicationId {
  static final String id = "ru.alexeypan.wordcards"
}

class Modules {
  static final String wordlistApi = ":features-wordlist-api"
  static final String wordlistImpl = ":features-wordlist-impl"
}

class Release {
  static final int versionCode = 1
  static final String versionName = "1.0"
}

class Versions {
  static final String androidPlugin = "3.1.4"

  static final int compileSdk = 28
  static final int minSdk = 15
  static final int targetSdk = 28

  static final String kotlin = "1.2.70"
  static final String androidx = "1.0.0"
  static final String constraintLayout = "1.1.3"

  static final String junit = "1.1.3"
  static final String testRunner = "1.1.0-alpha3"
  static final String espresso = "3.1.0-alpha4"
}

class Libs {
  static final String androidPlugin = "com.android.tools.build:gradle:${Versions.androidPlugin}"

  static final String kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  static final String kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

  static final String material = "com.google.android.material:material:$Versions.androidx"

  static class Androidx {
    static final String annotations = "androidx.annotation:annotation:$Versions.androidx"
    static final String appcompat = "androidx.appcompat:appcompat:$Versions.androidx"
    static final String recyclerView = "androidx.recyclerview:recyclerview:$Versions.androidx"
    static final String constraintLayout = "androidx.constraintlayout:constraintlayout:$Versions.constraintLayout"
  }

  static class Test {
    static final String junit = "junit:junit:$Versions.junit"
    static final String testRunner = "androidx.test:runner:$Versions.testRunner"
    static final String espresso = "androidx.test.espresso:espresso-core:$Versions.espresso"
  }
}


