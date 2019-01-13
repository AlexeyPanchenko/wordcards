package ru.alexeypan.wordcards.app

import android.app.Application
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.api.WordListScope
import ru.alexeypan.wordcards.wordlist.impl.di.scope.WordListScopeImpl

class App : Application() {

  override fun onCreate() {
    super.onCreate()

    Injector.registerScope(DBScope::class.java, DBScope(this))
    Injector.registerScope(WordListScope::class.java, WordListScopeImpl())
  }
}