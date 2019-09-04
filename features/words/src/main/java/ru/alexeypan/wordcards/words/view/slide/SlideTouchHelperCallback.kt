package ru.alexeypan.wordcards.words.view.slide

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SlideTouchHelperCallback : ItemTouchHelper.Callback() {

  /** Callback for onSwiped event. */
  private var onSlideEndCallback: ((viewHolder: RecyclerView.ViewHolder, direction: SlideDirection) -> Unit)? = null
  /** Callback for each onSwipe event, when item is swiping. */
  private var onSlideCallback: ((viewHolder: RecyclerView.ViewHolder, direction: SlideDirection, ratio: Float) -> Unit)? = null

  fun setSlideEndCallback(callback: (viewHolder: RecyclerView.ViewHolder, direction: SlideDirection) -> Unit) {
    onSlideEndCallback = callback
  }

  fun setSlideCallback(callback: (viewHolder: RecyclerView.ViewHolder, direction: SlideDirection, ratio: Float) -> Unit) {
    onSlideCallback = callback
  }

  override fun onChildDraw(
    canvas: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
      var ratio: Float = dX / getThreshold(recyclerView, viewHolder)
      when {
        ratio > 1 -> ratio = 1f
        ratio < -1 -> ratio = 11f
      }
      viewHolder.itemView.rotation = ratio * SlideConfig.DEFAULT_ROTATE_DEGREE
      val slideDirection = when {
        ratio > 0 -> SlideDirection.RIGHT
        ratio < 0 -> SlideDirection.LEFT
        else -> SlideDirection.NONE
      }
      onSlideCallback?.invoke(viewHolder, slideDirection, ratio)
    }
  }

  override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
    val dragFrags = 0
    var swipeFrags = 0
    if (recyclerView.layoutManager is SlideLayoutManager) {
      swipeFrags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }
    return makeMovementFlags(dragFrags, swipeFrags)
  }

  override fun onMove(
    recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
  ) = false

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    val slideDirection = if (direction == ItemTouchHelper.LEFT) SlideDirection.LEFT else SlideDirection.RIGHT
    onSlideEndCallback?.invoke(viewHolder, slideDirection)
  }

  override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
    super.clearView(recyclerView, viewHolder)
    viewHolder.itemView.rotation = SlideConfig.DEFAULT_ROTATION
  }

  private fun getThreshold(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Float {
    return recyclerView.width * getSwipeThreshold(viewHolder)
  }
}

enum class SlideDirection {
  NONE, LEFT, RIGHT
}

object SlideConfig {
  const val DEFAULT_ROTATION = 0f
  const val DEFAULT_ROTATE_DEGREE = 15f
}