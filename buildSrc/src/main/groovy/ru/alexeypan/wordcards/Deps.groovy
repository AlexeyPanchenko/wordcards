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
}

class Libs {
  static final String androidPlugin = "com.android.tools.build:gradle:${Versions.androidPlugin}"

  static final String kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  static final String kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}
