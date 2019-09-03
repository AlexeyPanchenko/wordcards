package ru.alexeypan.wordcards.app.words

import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao
import ru.alexeypan.wordcards.injector.InjectorScopeProvider
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.wordlist.impl.Word
import ru.alexeypan.wordcards.wordlist.impl.WordsScope
import ru.alexeypan.wordcards.wordlist.impl.dependencies.WordsStorage

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

  override fun save(word: Word, categoryTitle: String) {
    wordsDao.save(word.toDb(categoryTitle))
    categoriesDao.save(categoriesDao.get(categoryTitle).apply { wordsCount++ })
  }

  override fun getAll(categoryTitle: String): List<Word> {
    return wordsDao.getAll(categoryTitle).map { it.toDomain() }
  }
}

private fun WordDB.toDomain(): Word {
  return Word(
    original = original,
    translate = translate
  )
}

private fun Word.toDb(categoryTitle: String): WordDB {
  return WordDB(
    categoryTitle = categoryTitle,
    original = original,
    translate = translate
  )
}