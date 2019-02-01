package ru.alexeypan.wordcards.categories.impl.list.ui

import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.core.ui.Toaster
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.wordlist.api.WordListStarter

class CategoriesPresenter(
  private val toaster: Toaster,
  private val addCategoryDialog: AddCategoryDialogWidget,
  private val wordListStarter: WordListStarter,
  private val categoriesDao: CategoriesDao
) : BasePresenter<CategoriesView>() {

  init {
    addCategoryDialog.setAddCategoryListener { category, position ->
      if (category.title.isEmpty()) {
        toaster.show("Empty")
        return@setAddCategoryListener
      }
      categoriesDao.save(CategoryDB(category.title).apply { if (category.id != null) id = category.id })
      if (position != null) {
        view?.updateCategory(category, position)
      } else {
        updateCategories()
      }
    }
    addCategoryDialog.revival()
  }

  override fun onVewAttached(view: CategoriesView) {
    super.onVewAttached(view)
    updateCategories()
  }

  fun onAddClicked() {
    addCategoryDialog.show()
  }

  fun onDeleteClicked(category: Category, position: Int) {
    category.id?.let {
      categoriesDao.remove(it)
      view?.removeCategoryFromList(position)
    }
  }

  fun onEditClicked(category: Category, position: Int) {
    addCategoryDialog.show(category, position)
  }

  fun onItemDropped(fromPosition: Int, toPosition: Int) {

  }

  private fun updateCategories() {
    val categories: List<Category> = categoriesDao.getAll().map { Category(it.title, it.id) }
    view?.updateList(categories)
  }

  fun onCategoryClicked(category: Category) {
    wordListStarter.start(category.id!!)
  }

}