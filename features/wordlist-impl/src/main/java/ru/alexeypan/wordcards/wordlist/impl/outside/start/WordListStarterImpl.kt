package ru.alexeypan.wordcards.wordlist.impl.outside.start

import androidx.appcompat.app.AppCompatActivity
import ru.alexeypan.wordcards.wordlist.api.WordListStarter
import ru.alexeypan.wordcards.wordlist.impl.ui.WordListActivity

class WordListStarterImpl(
  private val activity: AppCompatActivity
) : WordListStarter {

  override fun start(categoryId: Long) {
    activity.startActivity(WordListActivity.newIntent(activity, categoryId))
  }
}