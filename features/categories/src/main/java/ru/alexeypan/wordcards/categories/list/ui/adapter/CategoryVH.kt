package ru.alexeypan.wordcards.categories.list.ui.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.categories.R
import ru.alexeypan.wordcards.categories.list.ui.drag.DraggableViewHolder

internal class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView), DraggableViewHolder {

  companion object {
    private const val DEFAULT_SCALE: Float = 1f
    private const val DRAG_SCALE: Float = 1.15f
  }

  val title: TextView = itemView.findViewById(R.id.tvCategoryTitle)
  val more: ImageView = itemView.findViewById(R.id.ivMore)

  private val card: CardView = itemView.findViewById(R.id.cvCategory)
  private val cardDefaultColor = card.cardBackgroundColor

  override fun onItemSelected() {
    animateCard(DRAG_SCALE, object : AnimatorListenerAdapter() {
      override fun onAnimationStart(animation: Animator?) {
        super.onAnimationStart(animation)
        card.setCardBackgroundColor(Color.LTGRAY)
      }
    })
  }

  override fun onItemCleared() {
    animateCard(DEFAULT_SCALE, object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationStart(animation)
        card.setCardBackgroundColor(cardDefaultColor)
      }
    })
  }

  private fun animateCard(scale: Float, listener: Animator.AnimatorListener) {
    card.animate()
      .setListener(listener)
      .scaleX(scale)
      .scaleY(scale)
      .start()
  }
}