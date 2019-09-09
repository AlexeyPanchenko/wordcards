package ru.alexeypan.wordcards.words

import android.os.Bundle
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.words.edit.WordsEditFragment

class WordsActivity : BaseActivity() {

  private val wordsScope: WordsScope = Injector.get(WordsScope::class)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.words_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction().replace(
        R.id.container,
        WordsEditFragment.newInstance(wordsScope.inRoute.getCategoryId(intent) ?: -1)
      ).commit()
    }
  }
}