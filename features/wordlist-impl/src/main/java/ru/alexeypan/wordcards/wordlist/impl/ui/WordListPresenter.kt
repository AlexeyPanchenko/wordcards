package ru.alexeypan.wordcards.wordlist.impl.ui

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider
import ru.alexeypan.wordcards.core.ui.mvp.BasePresenter
import ru.alexeypan.wordcards.wordlist.db.WordDB
import ru.alexeypan.wordcards.wordlist.db.WordsDao
import ru.alexeypan.wordcards.wordlist.impl.Word
import ru.alexeypan.wordcards.wordlist.impl.WordMapper
import ru.alexeypan.wordcards.wordlist.impl.view.slide.SlideDirection

class WordListPresenter(
  private val categoryId: Long,
  private val wordsDao: WordsDao,
  private val categoriesDao: CategoriesDao,
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
      categoriesDao.save(categoriesDao.get(categoryId).apply { wordsCount ++ })
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

  fun onBackPressed() {
    view?.goToCategories()
  }

  override fun onVewAttached(view: WordListView) {
    super.onVewAttached(view)
    updateWords()
  }

  override fun onVewDetached() {
    super.onVewDetached()
    backgroundScope.launch {
      val cat = categoriesDao.get(categoryId)
      cat.wordsCount = wordsDao.getAll(categoryId).size
      categoriesDao.save(cat)
    }
  }

  private fun updateWords() {
    if (words.isEmpty()) {
      mainScope.launch {
        try {
          val wordsList: List<Word> = withContext(dispatcherProvider.background) {
            val wordsDB: List<WordDB> = wordsDao.getAll(categoryId)
            categoriesDao.save(categoriesDao.get(categoryId).apply { wordsCount = wordsDB.size })
            return@withContext wordMapper.fromDB(wordsDB)
          }
          words.clear()
          words.addAll(wordsList)
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