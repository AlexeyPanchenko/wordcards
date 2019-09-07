package ru.alexeypan.wordcards.categories.list.ui

import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.categories.data.CategoriesRepository
import ru.alexeypan.wordcards.core.ui.toaster.AndroidToaster

class CategoriesController(
  private val categoriesListWidget: CategoriesListWidget,
  private val addCategoryDialogWidget: AddCategoryDialogWidget,
  private val router: CategoriesRouter,
  private val toaster: AndroidToaster,
  private val categoriesRepository: CategoriesRepository
) {

  init {
    categoriesListWidget.setCategoriesProvider {
      return@setCategoriesProvider categoriesRepository.getCategories()
    }
    categoriesListWidget.setAddClickListener {
      addCategoryDialogWidget.show(Category.newCategory())
    }
    categoriesListWidget.setCategoryClickListener { category ->
      router.openWords(category.id)
    }
    categoriesListWidget.editClickListener = { category, position ->
      addCategoryDialogWidget.show(category, position)
    }
    categoriesListWidget.removeClickListener = { category, position ->
      categoriesRepository.remove(category)
      categoriesListWidget.removeCategory(position)
    }
    categoriesListWidget.setOnItemDropListener { fromPosition, toPosition ->
      categoriesRepository.move(fromPosition, toPosition)
    }

    addCategoryDialogWidget.setAddCategoryListener { category, position ->
      onCategoryEdited(category, position)
    }
    addCategoryDialogWidget.revival()
    categoriesListWidget.updateList()
  }

  private fun onCategoryEdited(category: Category, position: Int?) {
    if (category.title.isEmpty()) {
      toaster.show("Empty")
      return
    }
    if (categoriesRepository.getCategories().contains(category)) {
      return
    }
    val correctPosition: Int = position ?: categoriesRepository.getCategories().size
    if (position == null) {
      categoriesRepository.add(category, correctPosition)
    } else {
      categoriesRepository.save(category, correctPosition)
    }
    categoriesListWidget.updateCategory(category, correctPosition)
  }
}