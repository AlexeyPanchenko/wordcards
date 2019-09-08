package ru.alexeypan.wordcards.app.categories

import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDao

class AppCategoriesStorage(
  private val categoriesDao: CategoriesDao,
  private val categoryWordDao: CategoryWordDao
) : CategoriesStorage {

  override fun getAll(): List<Category> {
    return categoriesDao.getAll().map { category ->
      return@map createCategory(category)
    }
  }

  override fun get(categoryId: Long): Category {
    return createCategory(categoriesDao.getCategory(categoryId))
  }

  override fun saveAll(categories: List<Category>) {
    categoriesDao.updatePositions(categories.map { it.id })
  }

  override fun add(category: Category, position: Int): Long {
    return categoriesDao.save(category.toDb(position))
  }

  override fun remove(category: Category) {
    categoriesDao.remove(category.id)
  }

  private fun createCategory(category: CategoryDB): Category {
    val wordCount: Int = categoryWordDao.getWordsCount(category.id)
    return category.toDomain(wordCount)
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