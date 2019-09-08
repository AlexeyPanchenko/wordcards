package ru.alexeypan.wordcards.categories.dependencies

import ru.alexeypan.wordcards.categories.Category

interface CategoriesStorage {
  fun getAll(): List<Category>
  fun get(categoryId: Long): Category
  fun saveAll(categories: List<Category>)
  fun add(category: Category, position: Int): Long
  fun remove(category: Category)
}