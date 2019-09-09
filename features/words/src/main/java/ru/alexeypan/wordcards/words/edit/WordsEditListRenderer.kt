package ru.alexeypan.wordcards.words.edit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.alexeypan.wordcards.core.ui.recycler.BaseVH
import ru.alexeypan.wordcards.core.ui.recycler.VHRenderer
import ru.alexeypan.wordcards.words.R
import ru.alexeypan.wordcards.words.Word

class WordsEditListRenderer : VHRenderer<WordsEditListVH, Word> {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsEditListVH {
    val context: Context = parent.context
    return WordsEditListVH(LayoutInflater.from(context).inflate(R.layout.words_edit_list_item, parent, false))
  }

  override fun onBindViewHolder(holder: WordsEditListVH, position: Int, model: Word?) {
    holder.original.setText(model?.original)
    holder.translate.setText(model?.translate)
  }
}

class WordsEditListVH(itemView: View) : BaseVH(itemView) {
  val original: TextView = itemView.findViewById(R.id.original)
  val translate: TextView = itemView.findViewById(R.id.translate)
}