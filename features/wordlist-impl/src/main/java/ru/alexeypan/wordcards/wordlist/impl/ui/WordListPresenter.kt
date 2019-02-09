package ru.alexeypan.wordcards.wordlist.impl.ui

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.wordlist.db.WordsDao
import ru.alexeypan.wordcards.wordlist.impl.Word
import ru.alexeypan.wordcards.wordlist.impl.WordMapper
import ru.alexeypan.wordcards.wordlist.impl.view.slide.SlideDirection

class WordListPresenter(
  private val categoryId: Long,
  private val wordsDao: WordsDao,
  private val wordMapper: WordMapper,
  dispatcherProvider: DispatcherProvider
) : BasePresenter<WordListView>(dispatcherProvider) {

  private val words = arrayListOf<Word>()

  fun provideWords(): List<Word> {
    return words
  }

  fun onAddClicked() {
    view?.openAddWord()
  }

  fun onWordAdded(word: Word) {
    words.add(word)
    backgroundScope.launch {
      wordsDao.save(wordMapper.toDB(categoryId, word))
    }
    view?.addCard(words.size)
  }

  fun onCardSlided(position: Int, direction: SlideDirection) {
    words.removeAt(position)
    view?.updateList()
  }

  fun onWordClicked(word: Word, position: Int) {
    words.set(position, word.apply { toggleState() })
    view?.updateCard(word, position)
  }

  override fun onVewAttached(view: WordListView) {
    super.onVewAttached(view)
    updateWords()
  }

  private fun updateWords() {
    if (words.isEmpty()) {
      mainScope.launch {
        try {
          val categoryList: List<Word> = withContext(dispatcherProvider.background) {
            return@withContext wordMapper.fromDB(wordsDao.getAll(categoryId))
          }
          words.clear()
          words.addAll(categoryList)
          view?.updateList()
        } catch (e: Exception) {
          view?.toaster()?.show("Error")
        }
      }
    } else{
      view?.updateList()
    }
  }
}