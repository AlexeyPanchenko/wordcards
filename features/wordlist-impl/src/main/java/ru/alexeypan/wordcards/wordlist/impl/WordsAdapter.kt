package ru.alexeypan.wordcards.wordlist.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter : RecyclerView.Adapter<WordHolder>() {

  private val words = arrayListOf<Word>()

  fun setItems(list: List<Word>) {
    words.clear()
    words.addAll(list)
    notifyDataSetChanged()
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
    holder.original.text = words[position].original
    holder.translate.text = words[position].translate
  }

  override fun getItemCount(): Int {
    return words.size
  }
}

class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val original: TextView = itemView.findViewById(R.id.tvWordOriginal)
  val translate: TextView = itemView.findViewById(R.id.tvWordTranslate)
}