package ru.alexeypan.wordcards.wordlist.impl

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SlideLayautManager(
  private val recyclerView: RecyclerView,
  private val touchHelper: ItemTouchHelper
) : RecyclerView.LayoutManager() {


  override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
    return RecyclerView.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )
  }

  override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
    detachAndScrapAttachedViews(recycler)
    val iCount = itemCount
    if (iCount > 3) {
      for (position in 3 downTo 0) {
        val view = recycler.getViewForPosition(position)
        addView(view)
        measureChildWithMargins(view, 0, 0)
        val widthSpace = width - getDecoratedMeasuredWidth(view)
        val heightSpace = height - getDecoratedMeasuredHeight(view)
        layoutDecoratedWithMargins(
          view,
          widthSpace / 2,
          heightSpace / 5,
          widthSpace / 2 + getDecoratedMeasuredWidth(view),
          heightSpace / 5 + getDecoratedMeasuredHeight(view)
        )
        when {
          position == 3 -> {
            view.scaleX = 1 - (position - 1) * 0.1f
            view.scaleY = 1 - (position - 1) * 0.1f
            view.translationY = (position - 1) * view.measuredHeight / 14f
          }
          position > 0 -> {
            view.scaleX = 1 - position * 0.1f
            view.scaleY = 1 - position * 0.1f
            view.translationY = position * view.measuredHeight / 14f
          }
          else -> {
            //Toast.makeText(view.context, "Position 1", Toast.LENGTH_SHORT).show()
            view.setOnTouchListener(mOnTouchListener)
          }
        }
      }
    } else {
      for (position in iCount - 1 downTo 0) {
        val view = recycler.getViewForPosition(position)
        addView(view)
        measureChildWithMargins(view, 0, 0)
        val widthSpace = width - getDecoratedMeasuredWidth(view)
        val heightSpace = height - getDecoratedMeasuredHeight(view)
        layoutDecoratedWithMargins(
          view,
          widthSpace / 2,
          heightSpace / 5,
          widthSpace / 2 + getDecoratedMeasuredWidth(view),
          heightSpace / 5 + getDecoratedMeasuredHeight(view)
        )

        if (position > 0) {
          view.scaleX = 1 - position * 0.1f
          view.scaleY = 1 - position * 0.1f
          view.translationY = position * view.measuredHeight / 14f
        } else {
          view.setOnTouchListener(mOnTouchListener)
        }
      }
    }
  }

  private val mOnTouchListener = View.OnTouchListener { v, event ->
    val childViewHolder = recyclerView.getChildViewHolder(v)
    if (MotionEventCompat.getActionMasked(event) === MotionEvent.ACTION_MOVE) {
      touchHelper.startSwipe(childViewHolder)
    }
    false
  }

}