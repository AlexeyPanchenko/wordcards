package ru.alexeypan.wordcards.categories.impl.list.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.category_list.*
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R
import ru.alexeypan.wordcards.categories.impl.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.categories.impl.list.CategoriesAdapter
import ru.alexeypan.wordcards.categories.impl.list.drag.DragItemTouchHelperCallback
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.AndroidToaster
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.api.WordListScope

class CategoriesActivity : BaseActivity(), CategoriesView {

  companion object {
    private const val COLUMN_COUNT = 3
  }

  private lateinit var dbScope: DBScope
  private lateinit var wordListScope: WordListScope

  private lateinit var adapter: CategoriesAdapter
  private lateinit var presenter: CategoriesPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    dbScope = Injector.openScope(DBScope::class.java)
    wordListScope = Injector.openScope(WordListScope::class.java)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.category_list)

    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener {  }

    initList()

    fabAdd.setOnClickListener { presenter.onAddClicked() }

    presenter = CategoriesPresenter(
      AndroidToaster(this),
      AddCategoryDialogWidget(this, stateProvider.stateRegistry("dialog"), lifecycle),
      wordListScope.wordListModule().getStarter(this),
      dbScope.appDatabase().categoriesDao()
    )
    presenter.onVewAttached(this)
  }

  override fun updateCategory(category: Category, position: Int) {
    adapter.updateItem(category, position)
  }

  override fun removeCategoryFromList(position: Int) {
    adapter.removeItem(position)
  }

  override fun updateList(categories: List<Category>) {
    adapter.clear()
    adapter.setItems(categories)
  }

  override fun onDestroy() {
    super.onDestroy()
    Injector.closeScope(WordListScope::class.java)
    presenter.onVewDetached()
  }

  private fun showPopupDialog(view: View, category: Category, position: Int) {
    val popupMenu = PopupMenu(this, view)
    popupMenu.inflate(R.menu.category_item_menu)
    popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
      when (item?.itemId) {
        R.id.menuEdit -> presenter.onEditClicked(category, position)
        R.id.menuDelete -> presenter.onDeleteClicked(category, position)
      }
      return@setOnMenuItemClickListener true
    }
    popupMenu.show()
  }

  private fun initList() {
    adapter = CategoriesAdapter()
    adapter.setCategoryClickListener { presenter.onCategoryClicked(it) }
    adapter.setMoreClickListener { view, category, position -> showPopupDialog(view, category, position) }
    rvList.adapter = adapter
    rvList.layoutManager = GridLayoutManager(this, COLUMN_COUNT)

    val touchHelperCallback = DragItemTouchHelperCallback()
    touchHelperCallback.setOnItemDragListener { fromPosition, toPosition ->
      adapter.moveItems(fromPosition, toPosition)
    }
    touchHelperCallback.setOnItemDropListener { fromPosition, toPosition ->
      presenter.onItemDropped(fromPosition, toPosition)
    }
    val touchHelper = ItemTouchHelper(touchHelperCallback)
    touchHelper.attachToRecyclerView(rvList)
  }

}