package ru.alexeypan.wordcards.categories.impl.list.ui

import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.core.ui.mvp.BaseView

interface CategoriesView : BaseView {
  fun updateCategory(category: Category, position: Int)
  fun removeCategoryFromList(position: Int)
  fun updateList(categories: List<Category>)
  fun openAddCategory(category: Category, position: Int? = null)
  fun moveItems(fromPosition: Int, toPosition: Int)
}