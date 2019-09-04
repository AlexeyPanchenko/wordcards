package ru.alexeypan.wordcards.categories.list.ui

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.R
import ru.alexeypan.wordcards.categories.list.ui.adapter.CategoriesAdapter
import ru.alexeypan.wordcards.categories.list.ui.adapter.CategoriesProvider
import ru.alexeypan.wordcards.categories.list.ui.drag.DragItemTouchHelperCallback
import ru.alexeypan.wordcards.categories.list.ui.drag.ItemChangePositionListener

private const val COLUMN_COUNT = 3

class CategoriesListWidget(
  private val list: RecyclerView,
  private val addButton: View
) {

  var editClickListener: ((Category, Int) -> Unit)? = null
  var removeClickListener: ((Category, Int) -> Unit)? = null
  private val context: Context = list.context
  private val adapter = CategoriesAdapter()
  private val touchHelperCallback = DragItemTouchHelperCallback()

  init {
    adapter.setMoreClickListener { view, category, position -> showPopupDialog(view, category, position) }
    list.adapter = adapter
    list.layoutManager = GridLayoutManager(context, COLUMN_COUNT)
    val touchHelper = ItemTouchHelper(touchHelperCallback)
    touchHelper.attachToRecyclerView(list)
    touchHelperCallback.setOnItemDragListener { fromPosition, toPosition ->
      adapter.notifyItemMoved(fromPosition, toPosition)
    }
  }

  fun updateList() {
    adapter.notifyDataSetChanged()
  }

  fun updateCategory(category: Category, position: Int) {
    adapter.notifyItemChanged(position, category)
  }

  fun removeCategory(position: Int) {
    adapter.notifyItemRemoved(position)
  }

  fun setAddClickListener(listener: () -> Unit) {
    addButton.setOnClickListener { listener.invoke() }
  }

  fun setCategoriesProvider(provider: CategoriesProvider) {
    adapter.setCategoriesProvider(provider)
  }

  fun setCategoryClickListener(listener: (category: Category) -> Unit) {
    adapter.setCategoryClickListener(listener)
  }

  fun setOnItemDropListener(listener: ItemChangePositionListener) {
    touchHelperCallback.setOnItemDropListener(listener)
  }

  private fun showPopupDialog(view: View, category: Category, position: Int) {
    val popupMenu = PopupMenu(context, view)
    popupMenu.inflate(R.menu.categories_item_menu)
    popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
      when (item?.itemId) {
        R.id.menuEdit -> editClickListener?.invoke(category, position)
        R.id.menuDelete -> removeClickListener?.invoke(category, position)
      }
      return@setOnMenuItemClickListener true
    }
    popupMenu.show()
  }
}