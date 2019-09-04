package ru.alexeypan.wordcards.categories.list.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.categories.data.CategoriesRepository
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.toaster.AndroidToaster
import ru.alexeypan.wordcards.core.ui.utils.CollectionUtils

class CategoriesController(
  private val categoriesListWidget: CategoriesListWidget,
  private val addCategoryDialogWidget: AddCategoryDialogWidget,
  private val router: CategoriesRouter,
  private val toaster: AndroidToaster,
  private val dispatcherProvider: DispatcherProvider,
  private val categoriesRepository: CategoriesRepository
) {

  /** Coroutine Scope for Main Thread  */
  private val mainScope: CoroutineScope = CoroutineScope(dispatcherProvider.main)
  /** Coroutine Scope for IO Thread  */
  private val backgroundScope: CoroutineScope = CoroutineScope(dispatcherProvider.background)
  private val categories = arrayListOf<Category>()

  init {
    categoriesListWidget.setCategoriesProvider {
      return@setCategoriesProvider categories
    }
    categoriesListWidget.setAddClickListener {
      addCategoryDialogWidget.show(Category.newCategory())
    }
    categoriesListWidget.setCategoryClickListener { category ->
      router.openWords(category.title)
    }
    categoriesListWidget.editClickListener = { category, position ->
      addCategoryDialogWidget.show(category, position)
    }
    categoriesListWidget.removeClickListener = { category, position ->
      categories.removeAt(position)
      backgroundScope.launch {
        categoriesRepository.remove(category)
      }
      categoriesListWidget.removeCategory(position)
    }
    categoriesListWidget.setOnItemDropListener { fromPosition, toPosition ->
      backgroundScope.launch {
        CollectionUtils.moveItem(categories, fromPosition, toPosition)
        categoriesRepository.save(categories)
      }
    }

    addCategoryDialogWidget.setAddCategoryListener { category, position ->
      onCategoryEdited(category, position)
    }
    addCategoryDialogWidget.revival()
    updateCategories()
  }

  private fun onCategoryEdited(category: Category, position: Int?) {
    if (category.title.isEmpty()) {
      toaster.show("Empty")
      return
    }
    if (categories.contains(category)) {
      return
    }
    val correctPosition: Int = position ?: categories.size
    category.position = correctPosition
    backgroundScope.launch {
      categoriesRepository.save(category)
    }
    if (position == null) {
      categories.add(category)
    } else {
      categories.set(correctPosition, category)
    }
    categoriesListWidget.updateCategory(category, correctPosition)
  }

  private fun updateCategories() {
    if (categories.isEmpty()) {
      mainScope.launch {
        try {
          val categoryList: List<Category> = withContext(dispatcherProvider.background) {
            return@withContext categoriesRepository.getCategories()
          }
          categories.clear()
          categories.addAll(categoryList)
          categoriesListWidget.updateList()
        } catch (e: Exception) {
          toaster.show("Error")
        }
      }
    } else {
      categoriesListWidget.updateList()
    }
  }
}