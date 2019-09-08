package ru.alexeypan.wordcards.app.words

import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDB
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDao
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao
import ru.alexeypan.wordcards.words.Word
import ru.alexeypan.wordcards.words.dependencies.WordsStorage

class AppWordsStorage(
  private val wordsDao: WordsDao,
  private val categoryWordDao: CategoryWordDao
) : WordsStorage {

  override fun save(word: Word, categoryId: Long) {
    val wordId: Long = wordsDao.upsert(word.toDb(categoryId))
    categoryWordDao.upsert(CategoryWordDB(categoryId, wordId))
  }

  override fun getAll(categoryId: Long): List<Word> {
    return wordsDao.getAll(categoryId).map { it.toDomain() }
  }
}

private fun WordDB.toDomain(): Word {
  return Word(
    id = id,
    original = original,
    translate = translate
  )
}

private fun Word.toDb(categoryId: Long): WordDB {
  return WordDB(
    categoryId = categoryId,
    original = original,
    translate = translate
  )
}