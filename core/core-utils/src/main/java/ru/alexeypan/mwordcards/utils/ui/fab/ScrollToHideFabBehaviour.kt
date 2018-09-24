package ru.alexeypan.mwordcards.utils.ui.fab

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScrollToHideFabBehaviour : FloatingActionButton.Behavior {

  constructor() : super()
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

  private val hiddenListener = FabCorrectHiddenListener()

  override fun onNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: FloatingActionButton,
    target: View,
    dxConsumed: Int,
    dyConsumed: Int,
    dxUnconsumed: Int,
    dyUnconsumed: Int,
    type: Int
  ) {
    super.onNestedScroll(
      coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type
    )
    if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
      child.show()
    } else if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
      child.hide(hiddenListener)
    }
  }

  override fun onStartNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: FloatingActionButton,
    directTargetChild: View,
    target: View,
    axes: Int,
    type: Int
  ): Boolean {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL
  }

}

class FabCorrectHiddenListener : FloatingActionButton.OnVisibilityChangedListener() {
  override fun onHidden(fab: FloatingActionButton?) {
    super.onHidden(fab)
    fab?.visibility = View.INVISIBLE
  }
}