package ru.alexeypan.wordcards.app.words

import ru.alexeypan.wordcards.categories.CategoriesScope
import ru.alexeypan.wordcards.categories.data.CategoriesRepository
import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDB
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDao
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao
import ru.alexeypan.wordcards.injector.InjectorScopeProvider
import ru.alexeypan.wordcards.injector.ScopeFactory
import ru.alexeypan.wordcards.words.Word
import ru.alexeypan.wordcards.words.WordsScope
import ru.alexeypan.wordcards.words.dependencies.CategoriesCacheCleaner
import ru.alexeypan.wordcards.words.dependencies.WordsStorage

class WordsScopeFactory : ScopeFactory<WordsScope> {

  private val dbScopeProvider = InjectorScopeProvider(DBScope::class)
  private val categoriesScopeProvider = InjectorScopeProvider(CategoriesScope::class)

  override fun create(): WordsScope {
    val database: AppDatabase = dbScopeProvider.get().database
    val categoriesRepository: CategoriesRepository = categoriesScopeProvider.get().categoriesRepository
    return WordsScope(
      wordsStorage = AppWordsStorage(database.wordsDao(), database.categoryWordDao()),
      categoryCacheCleaner = CategoriesCacheCleaner {
        categoriesRepository.clearCache()
      }
    )
  }
}

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