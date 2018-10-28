package ru.alexeypan.wordcards.wordlist.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.word_list_activity.*

class WordListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(rvList)
    val adapter = WordsAdapter()
    rvList.adapter = adapter
    adapter.setItems(listOf("First", "asdsa kjasdbkjsdas d as", "123fdfsd", "sddasd", "!WSWEFSAFSAKJFNSJKFN SAF NASL NSA", "sadas"))

  }
}