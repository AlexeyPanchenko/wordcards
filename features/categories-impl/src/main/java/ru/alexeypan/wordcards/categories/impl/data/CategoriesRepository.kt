package ru.alexeypan.wordcards.categories.impl.data

import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB

class CategoriesRepository(
  private val dao: CategoriesDao
) {

  fun getCategories(): List<CategoryDB> {
    return dao.getAll()
  }

  fun save(categories: List<CategoryDB>) {
    dao.saveAll(categories)
  }

  fun save(category: CategoryDB): Long {
    return dao.save(category)
  }

  fun remove(id: Long) {
    dao.remove(id)
  }

}