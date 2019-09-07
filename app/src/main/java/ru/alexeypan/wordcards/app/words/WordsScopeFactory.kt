package ru.alexeypan.wordcards.app.words

import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao
import ru.alexeypan.wordcards.injector.InjectorScopeProvider
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.words.Word
import ru.alexeypan.wordcards.words.WordsScope
import ru.alexeypan.wordcards.words.dependencies.WordsStorage

class WordsScopeFactory : ScopeFactory<WordsScope> {

  private val dbScope = InjectorScopeProvider(DBScope::class)

  override fun create(): WordsScope {
    val database: AppDatabase = dbScope.get().database
    return WordsScope(
      wordsStorage = AppWordsStorage(database.wordsDao(), database.categoriesDao())
    )
  }
}

class AppWordsStorage(
  private val wordsDao: WordsDao,
  private val categoriesDao: CategoriesDao
) : WordsStorage {

  override fun save(word: Word, categoryId: Long) {
    wordsDao.save(word.toDb(categoryId))
    //categoriesDao.save(categoriesDao.get(categoryTitle).apply { wordsCount++ })
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