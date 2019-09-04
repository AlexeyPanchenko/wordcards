package ru.alexeypan.wordcards.categories.dependencies

import ru.alexeypan.wordcards.categories.Category

interface CategoriesStorage {
  fun getAll(): List<Category>
  fun saveAll(categories: List<Category>)
  fun save(category: Category)
  fun remove(category: Category)
}