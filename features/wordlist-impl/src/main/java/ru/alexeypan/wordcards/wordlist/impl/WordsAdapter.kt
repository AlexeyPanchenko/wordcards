package ru.alexeypan.wordcards.wordlist.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class WordsAdapter : RecyclerView.Adapter<WordHolder>() {

  private val words = arrayListOf<Word>()

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
  }

  override fun getItemCount(): Int {
    return words.size
  }
}

class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val card: MaterialCardView = itemView.findViewById(R.id.cardWord)
  val wordView: TextView = itemView.findViewById(R.id.tvWord)
}