package ru.alexeypan.wordcards.wordlist.impl

import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SlideLayoutManager(
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
    if (itemCount > 0) {
      val view = recycler.getViewForPosition(0)
      addView(view)
      prepareCurrentView(view)
      view.setOnTouchListener(mOnTouchListener)
    }
  }

  private fun prepareCurrentView(view: View) {
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
  }

  private val mOnTouchListener = View.OnTouchListener { v, event ->
    val childViewHolder = recyclerView.getChildViewHolder(v)
    if (MotionEventCompat.getActionMasked(event) === MotionEvent.ACTION_MOVE) {
      touchHelper.startSwipe(childViewHolder)
    }
    false
  }

}