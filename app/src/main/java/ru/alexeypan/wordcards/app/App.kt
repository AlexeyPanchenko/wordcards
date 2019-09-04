package ru.alexeypan.wordcards.app

import android.app.Application
import ru.alexeypan.wordcards.app.categories.CategoriesScopeFactory
import ru.alexeypan.wordcards.app.words.WordsScopeFactory
import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.words.WordsScope

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    initScopeTree(this)
  }

}

private fun initScopeTree(app: Application) {
  val injector = Injector.getInstance()
  injector.putScope(DBScope::class, ScopeFactory { return@ScopeFactory DBScope(app) })
  injector.putScope(CategoriesScope::class, CategoriesScopeFactory())
  injector.putScope(WordsScope::class, WordsScopeFactory())
}