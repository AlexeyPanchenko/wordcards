package ru.alexeypan.wordcards.categories.impl.list.ui

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.CategoryMapper
import ru.alexeypan.wordcards.categories.impl.data.CategoriesRepository
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.core.ui.utils.CollectionUtils

class CategoriesPresenter(
  private val categoriesRepository: CategoriesRepository,
  private val categoryMapper: CategoryMapper,
  dispatcherProvider: DispatcherProvider
) : BasePresenter<CategoriesView>(dispatcherProvider) {

  private val categories = arrayListOf<Category>()

  override fun onVewAttached(view: CategoriesView) {
    super.onVewAttached(view)
    updateCategories()
  }

  fun onAddClicked() {
    view?.openAddCategory(Category.newCategory())
  }

  fun onDeleteClicked(category: Category, position: Int) {
    categories.removeAt(position)
    backgroundScope.launch {
      categoriesRepository.remove(category.id)
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
      categoriesRepository.save(categoryMapper.toDB(categories))
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
            return@withContext categoryMapper.fromDB(categoriesRepository.getCategories())
          }
          categories.clear()
          categories.addAll(categoryList)
          view?.updateList(categories)
        } catch (e: Exception) {
          view?.toaster()?.show("Error")
        }
      }
    } else{
      view?.updateList(categories)
    }
  }

  fun onCategoryClicked(category: Category) {
    view?.wordListStarter()?.start(category.id)
  }

  fun onCategoryEdited(category: Category, position: Int?) {
    if (category.title.isEmpty()) {
      view?.toaster()?.show("Empty")
      return
    }
    val correctPosition: Int = position ?: categories.size
    mainScope.launch {
      val id: Long = withContext(dispatcherProvider.background) {
        return@withContext categoriesRepository.save(categoryMapper.toDB(category, correctPosition))
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