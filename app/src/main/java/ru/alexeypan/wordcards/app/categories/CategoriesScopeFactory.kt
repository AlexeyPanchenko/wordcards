package ru.alexeypan.wordcards.app.categories

import android.app.Activity
import ru.alexeypan.wordcards.categories.impl.CategoriesScope
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.dependencies.CategoriesOutRoute
import ru.alexeypan.wordcards.categories.impl.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.injector.InjectorScopeProvider
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.wordlist.impl.WordsScope

class CategoriesScopeFactory : ScopeFactory<CategoriesScope> {

  private val dbScope = InjectorScopeProvider(DBScope::class)
  private val wordsScope = InjectorScopeProvider(WordsScope::class)

  override fun create(): CategoriesScope {
    return CategoriesScope(
      outRoute = object : CategoriesOutRoute {
        override fun openWords(activity: Activity, categoryTitle: String) {
          activity.startActivity(wordsScope.get().inRoute.intent(activity, categoryTitle))
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
    dao.saveAll(categories.map { it.toDb() })
  }

  override fun save(category: Category) {
    dao.save(category.toDb())
  }

  override fun remove(category: Category) {
    dao.remove(category.title)
  }
}

private fun CategoryDB.toDomain(): Category {
  return Category(
    title = title,
    image = image,
    wordsCount = wordsCount
  )
}

private fun Category.toDb(): CategoryDB {
  return CategoryDB(
    title = title,
    position = 0,
    image = image,
    wordsCount = wordsCount
  )
}
