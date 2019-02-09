package ru.alexeypan.wordcards.wordlist.impl.ui.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.wordlist.impl.R
import ru.alexeypan.wordcards.wordlist.impl.Word
import ru.alexeypan.wordcards.wordlist.impl.WordState

internal class WordsAdapter : RecyclerView.Adapter<WordHolder>() {

  private lateinit var wordsProvider: WordsProvider

  private var clickListener: ((word: Word, pos: Int) -> Unit)? = null

  fun setWordClickListener(listener: (word: Word, pos: Int) -> Unit) {
    clickListener = listener
  }

  fun setWordsProvider(provider: WordsProvider) {
    this.wordsProvider = provider
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
    return WordHolder(LayoutInflater.from(parent.context).inflate(R.layout.word_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: WordHolder, position: Int) {
    val word = wordsProvider.getWords()[holder.adapterPosition]
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
          clickListener?.invoke(word, holder.adapterPosition)
          endAnimation.start()
        }
      })
    }
    holder.card.setOnClickListener {
      startAnimation.start()
    }
  }

  override fun getItemCount(): Int {
    return wordsProvider.getWords().size
  }
}

internal interface WordsProvider {
  fun getWords(): List<Word>
}