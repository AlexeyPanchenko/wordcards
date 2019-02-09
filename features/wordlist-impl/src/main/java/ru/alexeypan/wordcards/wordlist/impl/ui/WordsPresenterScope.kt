package ru.alexeypan.wordcards.wordlist.impl.ui

import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.injector.Scope
import ru.alexeypan.wordcards.wordlist.db.WordsDao
import ru.alexeypan.wordcards.wordlist.impl.WordMapper

class WordsPresenterScope(
  private val categoryId: Long,
  private val wordsDao: WordsDao,
  private val wordMapper: WordMapper,
  private val dispatcherProvider: DispatcherProvider
) : Scope {

  private var presenter: WordListPresenter? = null

  fun presenter(): WordListPresenter = presenter!!

  override fun open() {
    if (presenter == null) {
      presenter = WordListPresenter(categoryId, wordsDao, wordMapper, dispatcherProvider)
    }
  }

  override fun close() {
    presenter = null
  }

}