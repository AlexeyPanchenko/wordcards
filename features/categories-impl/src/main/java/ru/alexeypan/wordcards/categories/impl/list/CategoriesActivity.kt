package ru.alexeypan.wordcards.categories.impl.list

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.category_list.*
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R
import ru.alexeypan.wordcards.categories.impl.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.categories.impl.list.drag.DragItemTouchHelperCallback
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.api.WordListScope

class CategoriesActivity : BaseActivity() {

  private lateinit var dbScope: DBScope
  private lateinit var wordListScope: WordListScope

  private lateinit var dao: CategoriesDao
  private lateinit var adapter: CategoriesAdapter
  private lateinit var addCategoryDialog: AddCategoryDialogWidget

  override fun onCreate(savedInstanceState: Bundle?) {
    dbScope = Injector.openScope(DBScope::class.java)
    wordListScope = Injector.openScope(WordListScope::class.java)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.category_list)

    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener { Toast.makeText(this, "REE", Toast.LENGTH_SHORT).show() }

    initList()

    dao = dbScope.appDatabase()?.categoriesDao()!!
    rebuildCategories()

    initAddCategoryDialog()
    fabAdd.setOnClickListener { addCategoryDialog.show() }
  }

  override fun onDestroy() {
    super.onDestroy()
    Injector.closeScope(WordListScope::class.java)
  }

  private fun showPopupDialog(view: View, category: Category, position: Int) {
    val popupMenu = PopupMenu(this, view)
    popupMenu.inflate(R.menu.category_item_menu)
    popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
      when (item?.itemId) {
        R.id.menuEdit -> addCategoryDialog.show(category, position)
        R.id.menuDelete -> removeCategory(category, position)
      }
      return@setOnMenuItemClickListener true
    }
    popupMenu.show()
  }

  private fun initList() {
    adapter = CategoriesAdapter()
    adapter.setCategoryClickListener { wordListScope.wordListModule().getStarter(this).start(it.id!!) }
    adapter.setMoreClickListener { view, category, position -> showPopupDialog(view, category, position) }
    rvList.adapter = adapter
    rvList.layoutManager = GridLayoutManager(this, 3)

    val touchHelperCallback = DragItemTouchHelperCallback()
    touchHelperCallback.setOnItemDragListener { fromPosition, toPosition ->
      adapter.moveItems(fromPosition, toPosition)
    }
    touchHelperCallback.setOnItemDropListener { fromPosition, toPosition ->

    }
    val touchHelper = ItemTouchHelper(touchHelperCallback)
    touchHelper.attachToRecyclerView(rvList)
  }

  private fun initAddCategoryDialog() {
    addCategoryDialog = AddCategoryDialogWidget(this, stateProvider.stateRegistry("dialog"), lifecycle)
    addCategoryDialog.setAddCategoryListener { category, position ->
      if (category.title.isEmpty()) {
        Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        return@setAddCategoryListener
      }
      dao.save(CategoryDB(category.title).apply { if (category.id != null) id = category.id })
      if (position != null) {
        adapter.updateItem(category, position)
      } else {
        rebuildCategories()
      }
    }
    addCategoryDialog.revival()
  }

  private fun removeCategory(category: Category, position: Int) {
    category.id?.let {
      dao.remove(it)
      adapter.removeItem(position)
    }
  }

  private fun rebuildCategories() {
    adapter.clear()
    dao.getAll().forEach { adapter.addItem(Category(it.title, it.id)) }
  }
}