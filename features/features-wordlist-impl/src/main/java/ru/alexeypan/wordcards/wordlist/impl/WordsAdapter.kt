package ru.alexeypan.wordcards.wordlist.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordsAdapter : RecyclerView.Adapter<WordHolder>() {

  private val words = arrayListOf<String>()

  fun setItems(list: List<String>) {
    words.clear()
    words.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
    return WordHolder(LayoutInflater.from(parent.context).inflate(R.layout.word_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: WordHolder, position: Int) {
    holder.tvWord.text = words[position]
  }

  override fun getItemCount(): Int {
    return words.size
  }
}

class WordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  val tvWord: TextView = itemView.findViewById(R.id.tvWord)
}