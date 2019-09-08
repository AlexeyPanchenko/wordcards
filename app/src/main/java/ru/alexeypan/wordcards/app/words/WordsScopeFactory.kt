package ru.alexeypan.wordcards.app.words

import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.categories.data.CategoriesRepository
import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.injector.InjectorScopeProvider
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.words.WordsScope
import ru.alexeypan.wordcards.words.dependencies.CategoryUpdateListener

class WordsScopeFactory : ScopeFactory<WordsScope> {

  private val dbScopeProvider = InjectorScopeProvider(DBScope::class)
  private val categoriesScopeProvider = InjectorScopeProvider(CategoriesScope::class)

  override fun create(): WordsScope {
    val database: AppDatabase = dbScopeProvider.get().database
    val categoriesRepository: CategoriesRepository = categoriesScopeProvider.get().categoriesRepository
    return WordsScope(
      wordsStorage = AppWordsStorage(database.wordsDao(), database.categoryWordDao()),
      categoryCacheCleaner = CategoryUpdateListener { categoryId ->
        categoriesRepository.updateCategory(categoryId)
      }
    )
  }
}