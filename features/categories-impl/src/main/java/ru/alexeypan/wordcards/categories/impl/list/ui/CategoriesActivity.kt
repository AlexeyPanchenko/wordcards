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
import ru.alexeypan.wordcards.categories.impl.list.ui.adapter.CategoriesAdapter
import ru.alexeypan.wordcards.categories.impl.list.ui.adapter.CategoriesProvider
import ru.alexeypan.wordcards.categories.impl.list.ui.drag.DragItemTouchHelperCallback
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.injector.Injector

class CategoriesActivity : BaseActivity(), CategoriesView {

  companion object {
    private const val COLUMN_COUNT = 3
  }

  private lateinit var presenterScope: CategoriesPresenterScope
  private lateinit var categoriesScope: CategoriesScope

  private lateinit var adapter: CategoriesAdapter
  private lateinit var presenter: CategoriesPresenter
  private lateinit var addCategoryDialogWidget: AddCategoryDialogWidget

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.category_list)
    initScopes()
    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener {  }
    initList()
    fabAdd.setOnClickListener { presenter.onAddClicked() }
    initAddCategoryDialog()
    initPresenter()
  }

  override fun updateCategory(category: Category, position: Int) {
    adapter.notifyItemChanged(position, category)
  }

  override fun removeCategoryFromList(position: Int) {
    adapter.notifyItemRemoved(position)
  }

  override fun updateList(categories: List<Category>) {
    adapter.notifyDataSetChanged()
  }

  override fun moveCategories(fromPosition: Int, toPosition: Int) {
    adapter.notifyItemMoved(fromPosition, toPosition)
  }

  override fun openAddCategory(category: Category, position: Int?) {
    addCategoryDialogWidget.show(category, position)
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onVewDetached()
    closeScopes()
  }

  private fun initScopes() {
    categoriesScope = Injector.openScope(CategoriesScope::class.java, CategoriesScope(this), true)
    presenterScope = Injector.openScope(
      CategoriesPresenterScope::class.java,
      CategoriesPresenterScope(Injector.openScope(DBScope::class.java), CategoryMapper())
    )
  }

  private fun initList() {
    adapter = CategoriesAdapter()
    adapter.setCategoryClickListener { presenter.onCategoryClicked(it) }
    adapter.setMoreClickListener { view, category, position -> showPopupDialog(view, category, position) }
    adapter.setCategoriesProvider(object : CategoriesProvider {
      override fun getCategories(): List<Category> {
        return presenter.provideCategories()
      }
    })
    rvList.adapter = adapter
    rvList.layoutManager = GridLayoutManager(this, COLUMN_COUNT)

    val touchHelperCallback = DragItemTouchHelperCallback()
    touchHelperCallback.setOnItemDragListener { fromPosition, toPosition ->
      presenter.onItemMove(fromPosition, toPosition)
    }
    touchHelperCallback.setOnItemDropListener { fromPosition, toPosition ->
      presenter.onItemDropped(fromPosition, toPosition)
    }
    val touchHelper = ItemTouchHelper(touchHelperCallback)
    touchHelper.attachToRecyclerView(rvList)
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

  private fun initAddCategoryDialog() {
    addCategoryDialogWidget = AddCategoryDialogWidget(this, stateProvider.stateRegistry("dialog"), lifecycle)
    addCategoryDialogWidget.setAddCategoryListener { category, position ->
      presenter.onCategoryEdited(category, position)
    }
    addCategoryDialogWidget.revival()
  }

  private fun initPresenter() {
    presenter = presenterScope.presenter(categoriesScope)
    presenter.onVewAttached(this)
  }

  private fun closeScopes() {
    Injector.closeScope(CategoriesScope::class.java, true)
    if (isFinishing) {
      Injector.closeScope(CategoriesPresenterScope::class.java, true)
    }
  }

}