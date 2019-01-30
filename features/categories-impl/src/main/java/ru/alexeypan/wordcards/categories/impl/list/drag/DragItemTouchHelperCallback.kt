package ru.alexeypan.wordcards.categories.impl.list.drag

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragItemTouchHelperCallback : ItemTouchHelper.Callback() {

  private var itemMoveListener: ((fromPosition: Int, toPosition: Int) -> Unit)? = null
  private var itemSwipeListener: ((position: Int) -> Unit)? = null

  fun setOnItemMoveListener(listener: (fromPosition: Int, toPosition: Int) -> Unit) {
    itemMoveListener = listener
  }

  override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
    val dragFlags: Int
    val swipeFlags: Int
    if (recyclerView.layoutManager is GridLayoutManager) {
      dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
      swipeFlags = 0
    } else {
      dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
      swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
    }
    return makeMovementFlags(dragFlags, swipeFlags)
  }

  override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
    if (viewHolder.itemViewType != target.itemViewType) {
      return false
    }
    itemMoveListener?.invoke(viewHolder.adapterPosition, target.adapterPosition)
    return true
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    itemSwipeListener?.invoke(viewHolder.adapterPosition)
  }

  override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder is DraggableViewHolder) {
      (viewHolder as DraggableViewHolder).onItemSelected()
    }
    super.onSelectedChanged(viewHolder, actionState)
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    super.clearView(recyclerView, viewHolder)
    if (viewHolder is DraggableViewHolder) {
      (viewHolder as DraggableViewHolder).onItemCleared()
    }
  }

  override fun isLongPressDragEnabled(): Boolean = true

  override fun isItemViewSwipeEnabled(): Boolean = true
}