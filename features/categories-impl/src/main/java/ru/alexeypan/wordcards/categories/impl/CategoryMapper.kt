package ru.alexeypan.wordcards.categories.impl

import ru.alexeypan.wordcards.categories.db.CategoryDB

class CategoryMapper {

  fun toDB(category: Category, position: Int): CategoryDB {
    val categoryDB = CategoryDB(category.title, position, category.image, category.wordsCount)
    if (category.existsId()) {
      categoryDB.id = category.id
    }
    return categoryDB
  }

  fun toDB(categories: List<Category>): List<CategoryDB> {
    return categories.mapIndexed { index: Int, category: Category -> toDB(category, index) }
  }

  fun fromDB(categoryDB: CategoryDB): Category {
    return Category(categoryDB.id, categoryDB.title, categoryDB.image, categoryDB.wordsCount)
  }

  fun fromDB(categories: List<CategoryDB>): List<Category> {
    return categories.map { category: CategoryDB -> fromDB(category) }
  }
}