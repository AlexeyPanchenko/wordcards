package ru.alexeypan.wordcards.app.categories

import android.app.Activity
import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.categories.dependencies.CategoriesOutRoute
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.injector.InjectorScopeProvider
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.words.WordsScope

class CategoriesScopeFactory : ScopeFactory<CategoriesScope> {

  private val dbScope = InjectorScopeProvider(DBScope::class)
  private val wordsScope = InjectorScopeProvider(WordsScope::class)

  override fun create(): CategoriesScope {
    return CategoriesScope(
      outRoute = object : CategoriesOutRoute {
        override fun openWords(activity: Activity, categoryId: Long) {
          activity.startActivity(wordsScope.get().inRoute.intent(activity, categoryId))
        }
      },
      categoriesStorage = AppCategoriesStorage(
        dbScope.get().database.categoriesDao(),
        dbScope.get().database.categoryWordDao()
      )
    )
  }
}
