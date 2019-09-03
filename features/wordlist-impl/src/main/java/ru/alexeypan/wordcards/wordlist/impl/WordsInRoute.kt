package ru.alexeypan.wordcards.wordlist.impl

import android.content.Context
import android.content.Intent
import ru.alexeypan.wordcards.wordlist.impl.ui.WordListActivity

private const val CATEGORY_TITLE = "category_title"

class WordsInRoute {

  fun intent(context: Context, categoryTitle: String): Intent {
    val intent = Intent(context, WordListActivity::class.java)
    intent.putExtra(CATEGORY_TITLE, categoryTitle)
    return intent
  }

  fun getCategoryTitle(intent: Intent): String? = intent.getStringExtra(CATEGORY_TITLE)
}