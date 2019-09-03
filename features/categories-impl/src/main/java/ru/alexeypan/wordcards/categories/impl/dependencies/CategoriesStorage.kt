package ru.alexeypan.wordcards.categories.impl.dependencies

import ru.alexeypan.wordcards.categories.impl.Category

interface CategoriesStorage {
  fun getAll(): List<Category>
  fun saveAll(categories: List<Category>)
  fun save(category: Category)
  fun remove(category: Category)
}