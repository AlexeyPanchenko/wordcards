package ru.alexeypan.wordcards.categories.impl.list.ui

import androidx.appcompat.app.AppCompatActivity
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.toaster.AndroidToaster
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.injector.Scope
import ru.alexeypan.wordcards.wordlist.api.WordListScope
import ru.alexeypan.wordcards.wordlist.api.WordListStarter

class CategoriesScope(private val activity: AppCompatActivity) : Scope {

  private var wordListStarter: WordListStarter? = null
  private var toaster: Toaster? = null

  fun wordListStarter(): WordListStarter = wordListStarter!!

  fun toaster(): Toaster = toaster!!

  override fun open() {
    val wordListScope = Injector.openScope(WordListScope::class.java)
    if (wordListStarter == null) {
      wordListStarter = wordListScope.wordListModule().getStarter(activity)
    }
    if (toaster == null) {
      toaster = AndroidToaster(activity)
    }
  }

  override fun close() {
    wordListStarter = null
    toaster = null
    Injector.closeScope(WordListScope::class.java)
  }
}

class CategoriesPresenterScope(
  private val dbScope: DBScope,
  private val categoryMapper: CategoryMapper,
  private val dispatcherProvider: DispatcherProvider
): Scope {

  private var presenter: CategoriesPresenter? = null

  fun presenter(categoriesScope: CategoriesScope): CategoriesPresenter {
    presenter?.init(categoriesScope.toaster(), categoriesScope.wordListStarter())
    return presenter!!
  }

  override fun open() {
    if (presenter == null) {
      presenter = CategoriesPresenter(dbScope.appDatabase().categoriesDao(), categoryMapper, dispatcherProvider)
    }
  }

  override fun close() {
    presenter = null
  }

}