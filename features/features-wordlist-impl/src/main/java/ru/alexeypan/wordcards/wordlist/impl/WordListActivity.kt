package ru.alexeypan.wordcards.wordlist.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_list_activity.*

class WordListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    rvList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(rvList)
    val adapter = WordsAdapter()
    rvList.adapter = adapter
    adapter.setItems(listOf(
      Word("Original1", "Translate1"),
      Word("Original2", "Translate2"),
      Word("Original3", "Translate3"),
      Word("Original4", "Translate4"),
      Word("Original5", "Translate5")
    ))
  }
}