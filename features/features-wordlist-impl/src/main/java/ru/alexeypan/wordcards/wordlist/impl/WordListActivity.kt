package ru.alexeypan.wordcards.wordlist.impl

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.word_list_activity.*

class WordListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener { Toast.makeText(this, "REE", Toast.LENGTH_SHORT).show() }
  }
}