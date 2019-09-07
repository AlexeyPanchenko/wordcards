package ru.alexeypan.wordcards.app.categories

import android.app.Activity
import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.dependencies.CategoriesOutRoute
import ru.alexeypan.wordcards.categories.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.category.CategoryDB
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
      categoriesStorage = AppCategoriesStorage(dbScope.get().database.categoriesDao())
    )
  }
}

class AppCategoriesStorage(
  private val dao: CategoriesDao
) : CategoriesStorage {

  override fun getAll(): List<Category> {
    return dao.getAll().map { it.toDomain() }
  }

  override fun saveAll(categories: List<Category>) {
    dao.saveAll(categories.mapIndexed { index: Int, category: Category -> category.toDb(index) })
  }

  override fun add(category: Category, position: Int): Long {
    return dao.save(category.toDb(position))
  }

  override fun remove(category: Category) {
    dao.remove(category.id)
  }
}

private fun CategoryDB.toDomain(): Category {
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
    image = image,
    wordsCount = wordsCount
  )
  if (hasId()) {
    category.id = id
  }
  return category
}
