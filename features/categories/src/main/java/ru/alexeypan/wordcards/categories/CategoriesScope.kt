package ru.alexeypan.wordcards.categories

import ru.alexeypan.wordcards.categories.dependencies.CategoriesOutRoute
import ru.alexeypan.wordcards.categories.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.injector.Scope

class CategoriesScope(
  internal val outRoute: CategoriesOutRoute,
  internal val categoriesStorage: CategoriesStorage
) : Scope {

}