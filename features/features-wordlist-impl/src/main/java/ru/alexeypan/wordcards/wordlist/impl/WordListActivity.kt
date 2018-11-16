package ru.alexeypan.wordcards.wordlist.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_list_activity.*

class WordListActivity : AppCompatActivity() {

  companion object {
    private const val CATEGORY_ID = "category_id"

    fun newIntent(context: Context, categoryId: Int): Intent {
      val intent = Intent(context, WordListActivity::class.java)
      intent.putExtra(CATEGORY_ID, categoryId)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    rvList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(rvList)
    val adapter = WordsAdapter()
    rvList.adapter = adapter
    Toast.makeText(this, "ID = ${intent.getIntExtra(CATEGORY_ID, -1)}", Toast.LENGTH_SHORT).show()
    adapter.setItems(listOf(
      Word("Original1", "Translate1"),
      Word("Original2", "Translate2"),
      Word("Original3", "Translate3"),
      Word("Original4", "Translate4"),
      Word("Original5", "Translate5")
    ))
  }
}