package ru.alexeypan.wordcards.categories.impl.list.ui

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.core.ui.utils.CollectionUtils
import ru.alexeypan.wordcards.wordlist.api.WordListStarter

class CategoriesPresenter(
  private val categoriesDao: CategoriesDao,
  private val categoryMapper: CategoryMapper,
  dispatcherProvider: DispatcherProvider
) : BasePresenter<CategoriesView>(dispatcherProvider) {

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
    backgroundScope.launch {
      categoriesDao.remove(category.id)
    }
    view?.removeCategoryFromList(position)
  }

  fun onEditClicked(category: Category, position: Int) {
    view?.openAddCategory(category, position)
  }

  fun onItemMove(fromPosition: Int, toPosition: Int) {
    view?.moveCategories(fromPosition, toPosition)
  }

  fun onItemDropped(fromPosition: Int, toPosition: Int) {
    backgroundScope.launch {
      CollectionUtils.moveItem(categories, fromPosition, toPosition)
      categoriesDao.saveAll(categoryMapper.toDB(categories))
    }
  }

  fun provideCategories(): List<Category> {
    return categories
  }

  private fun updateCategories() {
    if (categories.isEmpty()) {
      mainScope.launch {
        try {
          val categoryList: List<Category> = withContext(dispatcherProvider.background) {
            return@withContext categoriesDao.getAll().map { Category(it.id, it.title, it.image, it.wordsCount) }
          }
          categories.clear()
          categories.addAll(categoryList)
          view?.updateList(categories)
        } catch (e: Exception) {
          toaster?.show("Error")
        }
      }
    } else{
      view?.updateList(categories)
    }
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
    mainScope.launch {
      val id: Long = withContext(dispatcherProvider.background) {
        val databaseCategory = categoryMapper.toDB(category, correctPosition)
        return@withContext categoriesDao.save(databaseCategory)
      }

      if (category.existsId()) {
        categories.set(correctPosition, category)
      } else {
        category.id = id
        categories.add(category)
      }
      view?.updateCategory(category, correctPosition)
    }
  }

}