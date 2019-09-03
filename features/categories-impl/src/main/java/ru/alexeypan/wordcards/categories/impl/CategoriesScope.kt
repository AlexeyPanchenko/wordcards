package ru.alexeypan.wordcards.categories.impl

import ru.alexeypan.wordcards.categories.impl.dependencies.CategoriesOutRoute
import ru.alexeypan.wordcards.categories.impl.dependencies.CategoriesStorage
import ru.alexeypan.wordcards.injector.Scope

class CategoriesScope(
  internal val outRoute: CategoriesOutRoute,
  internal val categoriesStorage: CategoriesStorage
) : Scope {

}