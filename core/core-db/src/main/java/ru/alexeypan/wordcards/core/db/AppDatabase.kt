package ru.alexeypan.wordcards.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexeypan.wordcards.core.db.categories.CategoriesDao
import ru.alexeypan.wordcards.core.db.categories.CategoryDB
import ru.alexeypan.wordcards.core.db.words.WordDB
import ru.alexeypan.wordcards.core.db.words.WordsDao

@Database(entities = [WordDB::class, CategoryDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun wordsDao(): WordsDao
  abstract fun categoriesDao(): CategoriesDao

  companion object {
    private var instance: AppDatabase? = null
    fun getInstance(context: Context): AppDatabase? {
      if (instance == null) {
        synchronized(AppDatabase::class) {
          instance = Room
            .databaseBuilder(context, AppDatabase::class.java, "words-cards.db")
            .allowMainThreadQueries()
            .build()
        }
      }
      return instance
    }
    fun destroyInstance() {
      instance = null
    }
  }
}