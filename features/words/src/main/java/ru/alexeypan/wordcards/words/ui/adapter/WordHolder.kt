package ru.alexeypan.wordcards.words.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.words.R

internal class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val card: CardView = itemView.findViewById(R.id.cardWord)
  val wordView: TextView = itemView.findViewById(R.id.tvWord)

  init {
    val scale = itemView.resources.displayMetrics.density
    itemView.cameraDistance = 8000 * scale
  }
}