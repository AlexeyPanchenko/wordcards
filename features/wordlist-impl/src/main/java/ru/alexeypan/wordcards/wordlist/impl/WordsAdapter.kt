package ru.alexeypan.wordcards.wordlist.impl

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter : RecyclerView.Adapter<WordHolder>() {

  public val words = arrayListOf<Word>()

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

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
    return WordHolder(LayoutInflater.from(parent.context).inflate(R.layout.word_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: WordHolder, position: Int) {
    val word = words[position]
    holder.wordView.text = if (word.state == WordState.ORIGINAL) word.original else word.translate
    holder.itemView.setOnClickListener {
      val animator1 = ObjectAnimator.ofFloat(holder.itemView, "rotationY", 0f, 90f)
      val animator2 = ObjectAnimator.ofFloat(holder.itemView, "rotationY", -90f, 0f)
      animator1.duration = 300
      animator2.duration = 300
      animator1.interpolator = AccelerateInterpolator()
      animator2.interpolator = DecelerateInterpolator()
      animator1.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          clickListener?.invoke(word, position)
          animator2.start()
        }
      })
      animator1.start()
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