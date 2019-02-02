package ru.alexeypan.wordcards.categories.impl.list.ui.drag

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragItemTouchHelperCallback : ItemTouchHelper.Callback() {

  companion object {
    private const val NO_POSITION = -1
  }

  private var itemDragListener: ((fromPosition: Int, toPosition: Int) -> Unit)? = null
  private var itemDropListener: ((fromPosition: Int, toPosition: Int) -> Unit)? = null

  private var itemSwipeListener: ((position: Int) -> Unit)? = null

  private var fromPosition = NO_POSITION
  private var toPosition = NO_POSITION

  fun setOnItemDragListener(listener: (fromPosition: Int, toPosition: Int) -> Unit) {
    itemDragListener = listener
  }

  fun setOnItemDropListener(listener: (fromPosition: Int, toPosition: Int) -> Unit) {
    itemDropListener = listener
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
    val from = viewHolder.adapterPosition
    val to = target.adapterPosition

    if (fromPosition == NO_POSITION) {
      fromPosition = from
    }
    toPosition = to
    itemDragListener?.invoke(from, to)
    return true
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    itemSwipeListener?.invoke(viewHolder.adapterPosition)
  }

  override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
    if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is DraggableViewHolder) {
      (viewHolder as DraggableViewHolder).onItemSelected()
    }
    super.onSelectedChanged(viewHolder, actionState)
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    super.clearView(recyclerView, viewHolder)
    if (viewHolder is DraggableViewHolder) {
      (viewHolder as DraggableViewHolder).onItemCleared()

      if(fromPosition != NO_POSITION && toPosition != NO_POSITION && fromPosition != toPosition) {
        itemDropListener?.invoke(fromPosition, toPosition)
      }
      fromPosition = NO_POSITION
      toPosition = NO_POSITION
    }
  }

  override fun isLongPressDragEnabled(): Boolean = true

  override fun isItemViewSwipeEnabled(): Boolean = true
}