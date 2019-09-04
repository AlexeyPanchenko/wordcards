package ru.alexeypan.wordcards.categories.list.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.categories_list.*
import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.categories.R
import ru.alexeypan.wordcards.categories.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.core.ui.coroutines.BaseDispatcherProvider
import ru.alexeypan.wordcards.core.ui.toaster.AndroidToaster
import ru.alexeypan.wordcards.injector.Injector

class CategoriesActivity : BaseActivity() {

  private val categoriesScope = Injector.get(CategoriesScope::class)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.categories_list)
    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener { }

    CategoriesController(
      CategoriesListWidget(rvList, fabAdd),
      AddCategoryDialogWidget(this, stateProvider.stateRegistry("dialog"), lifecycle),
      CategoriesRouter(this, categoriesScope.outRoute),
      AndroidToaster(this),
      BaseDispatcherProvider(),
      categoriesScope.categoriesRepository
    )
  }
}