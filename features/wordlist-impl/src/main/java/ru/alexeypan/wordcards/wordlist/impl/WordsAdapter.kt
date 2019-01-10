package ru.alexeypan.wordcards.wordlist.impl

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter : RecyclerView.Adapter<WordHolder>() {

  val words = arrayListOf<Word>()

  private var clickListener: ((word: Word, pos: Int) -> Unit)? = null

  fun setItems(list: List<Word>) {
    words.clear()
    words.addAll(list)
    notifyDataSetChanged()
  }

  fun setListener(listener: (word: Word, pos: Int) -> Unit) {
    clickListener = listener
  }

  fun addItem(word: Word) {
    val position: Int = words.size
    words.add(word)
    notifyItemInserted(position)
  }

  fun removeItem(position: Int) {
    words.removeAt(position)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
    return WordHolder(LayoutInflater.from(parent.context).inflate(R.layout.word_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: WordHolder, position: Int) {
    val word = words[position]
    holder.wordView.text = if (word.state == WordState.ORIGINAL) word.original else word.translate

    val endAnimation = ValueAnimator.ofFloat(-90f, 0f).apply {
      duration = 300
      interpolator = DecelerateInterpolator()
      addUpdateListener { animation ->  holder.itemView.rotationY = animation.animatedValue as Float }
    }
    val startAnimation = ValueAnimator.ofFloat(0f, 90f).apply {
      duration = 300
      interpolator = AccelerateInterpolator()
      addUpdateListener { animation ->  holder.itemView.rotationY = animation.animatedValue as Float }
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          clickListener?.invoke(word, position)
          endAnimation.start()
        }
      })
    }
    holder.card.setOnClickListener {
      startAnimation.start()
    }
  }

  override fun getItemCount(): Int {
    return words.size
  }
}

class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val card: CardView = itemView.findViewById(R.id.cardWord)
  val wordView: TextView = itemView.findViewById(R.id.tvWord)

  init {
    val scale = itemView.resources.displayMetrics.density
    itemView.cameraDistance = 8000 * scale
  }
}