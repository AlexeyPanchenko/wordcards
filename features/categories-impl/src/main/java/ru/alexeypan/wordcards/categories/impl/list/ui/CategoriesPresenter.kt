package ru.alexeypan.wordcards.categories.impl.list.ui

import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.core.ui.Toaster
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.wordlist.api.WordListStarter
import java.util.*

class CategoriesPresenter(
  private val categoriesDao: CategoriesDao
) : BasePresenter<CategoriesView>() {

  private var toaster: Toaster? = null
  private var wordListStarter: WordListStarter? = null

  private val categories = arrayListOf<Category>()

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
    view?.openAddCategory(Category.newCategory())
  }

  fun onDeleteClicked(category: Category, position: Int) {
    categories.removeAt(position)
    categoriesDao.remove(category.id)
    view?.removeCategoryFromList(position)
  }

  fun onEditClicked(category: Category, position: Int) {
    view?.openAddCategory(category, position)
  }

  fun onItemMove(fromPosition: Int, toPosition: Int) {
    Collections.swap(categories, fromPosition, toPosition)
    view?.moveItems(fromPosition, toPosition)
  }

  fun onItemDropped(fromPosition: Int, toPosition: Int) {

  }

  fun provideCategories(): List<Category> {
    return categories
  }

  private fun updateCategories() {
    if (categories.isEmpty()) {
      categoriesDao.getAll().forEach { categories.add(Category(it.id, it.title)) }
    }
    view?.updateList(categories)
  }

  fun onCategoryClicked(category: Category) {
    wordListStarter?.start(category.id)
  }

  fun onCategoryEdited(category: Category, position: Int?) {
    if (category.title.isEmpty()) {
      toaster?.show("Empty")
      return
    }
    val correctPosition: Int = position ?: categories.size
    val databaseCategory = CategoryDB(category.title, correctPosition)
    if (category.id != Category.NO_ID) {
      databaseCategory.id = category.id
      val categoryId = categoriesDao.save(databaseCategory)
      category.id = categoryId
      categories[correctPosition] = category
    } else {
      categoriesDao.save(databaseCategory)
      categories.add(correctPosition, category)
    }
    view?.updateCategory(category, correctPosition)
  }

}