package ru.alexeypan.wordcards.categories.list.ui

import android.app.Activity
import ru.alexeypan.wordcards.categories.dependencies.CategoriesOutRoute

class CategoriesRouter(
  private val activity: Activity,
  private val outRoute: CategoriesOutRoute
) {

  fun openWords(categoryTitle: String) {
    outRoute.openWords(activity, categoryTitle)
  }
}