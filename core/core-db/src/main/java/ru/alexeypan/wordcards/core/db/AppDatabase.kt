package ru.alexeypan.wordcards.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDB
import ru.alexeypan.wordcards.core.db.category.word.CategoryWordDao
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao

internal const val APP_DATABASE_VERSION = 1
internal const val APP_DATABASE_NAME = "words.db"

@Database(
  entities = [CategoryDB::class, WordDB::class, CategoryWordDB::class],
  version = APP_DATABASE_VERSION
)

abstract class AppDatabase : RoomDatabase() {

  abstract fun categoriesDao(): CategoriesDao
  abstract fun wordsDao(): WordsDao
  abstract fun categoryWordDao(): CategoryWordDao
}