package ru.alexeypan.wordcards.words

import android.content.Context
import android.content.Intent

private const val CATEGORY_ID = "category_id"

class WordsInRoute {

  fun intent(context: Context, categoryId: Long): Intent {
    val intent = Intent(context, WordsActivity::class.java)
    intent.putExtra(CATEGORY_ID, categoryId)
    return intent
  }

  fun getCategoryId(intent: Intent): Long? = intent.getLongExtra(CATEGORY_ID, -1)
}