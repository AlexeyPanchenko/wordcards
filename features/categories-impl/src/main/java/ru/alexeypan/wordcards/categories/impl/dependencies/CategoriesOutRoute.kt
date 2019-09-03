package ru.alexeypan.wordcards.categories.impl.dependencies

import android.app.Activity

interface CategoriesOutRoute {
  fun openWords(activity: Activity, categoryTitle: String)
}