package ru.alexeypan.wordcards.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.wordlist.db.WordDB
import ru.alexeypan.wordcards.wordlist.db.WordsDao

@Database(entities = [CategoryDB::class, WordDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun categoriesDao(): CategoriesDao
  abstract fun wordsDao(): WordsDao

  companion object {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase? {
      if (instance == null) {
        synchronized(AppDatabase::class) {
          instance = Room
            .databaseBuilder(context, AppDatabase::class.java, "word-cards.db")
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