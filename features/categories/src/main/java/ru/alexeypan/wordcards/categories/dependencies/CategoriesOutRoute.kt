package ru.alexeypan.wordcards.categories.dependencies

import android.app.Activity

interface CategoriesOutRoute {
  fun openWords(activity: Activity, categoryId: Long)
}