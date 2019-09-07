package ru.alexeypan.wordcards.categories.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.utils.CollectionUtils

class CategoriesRepository(
  private val storage: CategoriesStorage,
  dispatcherProvider: DispatcherProvider
) {

  private val backgroundScope: CoroutineScope = CoroutineScope(dispatcherProvider.background)

  private val categories: MutableList<Category> = mutableListOf()

  fun getCategories(): List<Category> {
    if (categories.isEmpty()) {
      categories.addAll(storage.getAll())
    }
    return categories
  }

  fun move(from: Int, to: Int) {
    CollectionUtils.moveItem(categories, from, to)
    backgroundScope.launch {
      storage.saveAll(categories)
    }
  }

  fun add(category: Category, position: Int) {
    categories.add(category)
    backgroundScope.launch {
      val categoryId: Long = storage.add(category, position)
      categories.get(position).id = categoryId
    }
  }

  fun save(category: Category, position: Int) {
    categories.set(position, category)
    backgroundScope.launch {
      storage.add(category, position)
    }
  }

  fun remove(category: Category) {
    categories.remove(category)
    backgroundScope.launch {
      storage.remove(category)
    }
  }

}