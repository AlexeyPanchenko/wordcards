package ru.alexeypan.wordcards.categories.impl.list.ui

import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.core.ui.Toaster
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.wordlist.api.WordListStarter

class CategoriesPresenter(
  private val categoriesDao: CategoriesDao
) : BasePresenter<CategoriesView>() {

  private var toaster: Toaster? = null
  private var wordListStarter: WordListStarter? = null

  fun init(toaster: Toaster, wordListStarter: WordListStarter) {
    this.toaster = toaster
    this.wordListStarter = wordListStarter
  }

  override fun onVewAttached(view: CategoriesView) {
    super.onVewAttached(view)
    updateCategories()
  }

  override fun onVewDetached() {
    super.onVewDetached()
    this.toaster = null
    this.wordListStarter = null
  }

  fun onAddClicked() {
    view?.openAddCategory()
  }

  fun onDeleteClicked(category: Category, position: Int) {
    category.id?.let {
      categoriesDao.remove(it)
      view?.removeCategoryFromList(position)
    }
  }

  fun onEditClicked(category: Category, position: Int) {
    view?.openAddCategory(category, position)
  }

  fun onItemDropped(fromPosition: Int, toPosition: Int) {

  }

  private fun updateCategories() {
    val categories: List<Category> = categoriesDao.getAll().map { Category(it.title, it.id) }
    view?.updateList(categories)
  }

  fun onCategoryClicked(category: Category) {
    wordListStarter?.start(category.id!!)
  }

  fun onCategoryEdited(category: Category, position: Int?) {
    if (category.title.isEmpty()) {
        toaster?.show("Empty")
        return
      }
      categoriesDao.save(CategoryDB(category.title).apply { if (category.id != null) id = category.id })
      if (position != null) {
        view?.updateCategory(category, position)
      } else {
        updateCategories()
      }
  }

}