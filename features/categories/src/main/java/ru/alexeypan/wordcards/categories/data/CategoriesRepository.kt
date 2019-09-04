package ru.alexeypan.wordcards.categories.data

import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.dependencies.CategoriesStorage

class CategoriesRepository(
  private val storage: CategoriesStorage
) {

  fun getCategories(): List<Category> {
    return storage.getAll()
  }

  fun save(categories: List<Category>) {
    storage.saveAll(categories)
  }

  fun save(category: Category) {
    storage.save(category)
  }

  fun remove(category: Category) {
    storage.remove(category)
  }

}