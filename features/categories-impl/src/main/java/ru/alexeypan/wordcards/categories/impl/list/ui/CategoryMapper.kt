package ru.alexeypan.wordcards.categories.impl.list.ui

import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.categories.impl.Category

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
}