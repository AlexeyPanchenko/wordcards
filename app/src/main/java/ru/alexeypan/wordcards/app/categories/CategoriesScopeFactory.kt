package ru.alexeypan.wordcards.app.categories

import android.app.Activity
import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.dependencies.CategoriesOutRoute
import ru.alexeypan.wordcards.categories.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDao
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

class AppCategoriesStorage(
  private val categoriesDao: CategoriesDao,
  private val categoryWordDao: CategoryWordDao
) : CategoriesStorage {

  override fun getAll(): List<Category> {
    return categoriesDao.getAll().map { category ->
      val wordCount: Int = categoryWordDao.getWordsCount(category.id)
      return@map category.toDomain(wordCount)
    }
  }

  override fun saveAll(categories: List<Category>) {
    categoriesDao.saveAll(categories.mapIndexed { index: Int, category: Category -> category.toDb(index) })
  }

  override fun add(category: Category, position: Int): Long {
    return categoriesDao.save(category.toDb(position))
  }

  override fun remove(category: Category) {
    categoriesDao.remove(category.id)
  }
}

private fun CategoryDB.toDomain(wordsCount: Int): Category {
  return Category(
    id = id,
    title = title,
    image = image,
    wordsCount = wordsCount
  )
}

private fun Category.toDb(position: Int): CategoryDB {
  val category = CategoryDB(
    title = title,
    position = position,
    image = image
  )
  if (hasId()) {
    category.id = id
  }
  return category
}
