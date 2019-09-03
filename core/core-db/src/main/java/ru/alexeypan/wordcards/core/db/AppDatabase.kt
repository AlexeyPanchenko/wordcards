package ru.alexeypan.wordcards.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.alexeypan.wordcards.core.db.category.CategoriesDao
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao

internal const val APP_DATABASE_NAME = "word-cards.db"
internal const val APP_DATABASE_VERSION = 1

@Database(
  entities = [CategoryDB::class, WordDB::class],
  version = APP_DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun categoriesDao(): CategoriesDao
  abstract fun wordsDao(): WordsDao
}