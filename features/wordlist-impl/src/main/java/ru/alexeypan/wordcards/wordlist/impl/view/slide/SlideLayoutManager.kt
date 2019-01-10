package ru.alexeypan.wordcards.wordlist.impl.view.slide

import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager

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
      if (!ViewCompat.isAttachedToWindow(view)) {
        TransitionManager.beginDelayedTransition(recyclerView, Slide(Gravity.BOTTOM))
      }
      addView(view)
      prepareCurrentView(view)
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
    view.setOnTouchListener(mOnTouchListener)
  }

  private val mOnTouchListener = View.OnTouchListener { v, event ->
    val childViewHolder = recyclerView.getChildViewHolder(v)
    if (event.action == MotionEvent.ACTION_DOWN) {
      return@OnTouchListener false
    }
    if (event.action == MotionEvent.ACTION_MOVE) {
      touchHelper.startSwipe(childViewHolder)
    }
    return@OnTouchListener false
  }

}