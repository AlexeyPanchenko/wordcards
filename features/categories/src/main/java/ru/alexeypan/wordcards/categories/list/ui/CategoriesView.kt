package ru.alexeypan.wordcards.categories.list.ui

import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.core.ui.mvp.BaseView
import ru.alexeypan.wordcards.core.ui.toaster.Toaster

interface CategoriesView : BaseView {
  fun updateCategory(category: Category, position: Int)
  fun removeCategoryFromList(position: Int)
  fun updateList(categories: List<Category>)
  fun openAddCategory(category: Category, position: Int? = null)
  fun moveCategories(fromPosition: Int, toPosition: Int)
  fun toaster(): Toaster
}