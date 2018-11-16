package ru.alexeypan.wordcards.app

import android.app.Application
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.impl.scope.WordListScopeImpl

class App : Application() {

  override fun onCreate() {
    super.onCreate()

    Injector.wordListScope = WordListScopeImpl()
  }
}