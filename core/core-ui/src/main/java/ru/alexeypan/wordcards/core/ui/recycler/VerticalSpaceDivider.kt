package ru.alexeypan.wordcards.core.ui.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceDivider(
  private val space: Int,
  private val includeLast: Boolean = true
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
    super.getItemOffsets(outRect, view, parent, state)
    val currentPosition = parent.getChildAdapterPosition(view)
    val count: Int = parent.adapter?.itemCount ?: 0
    if (!includeLast && currentPosition == count - 1) {
      return
    }
    outRect.bottom = space
  }

}