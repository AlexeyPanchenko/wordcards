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
abstract class AppDatabaseImpl : RoomDatabase(), AppDatabase {
  abstract override fun categoriesDao(): CategoriesDao
  abstract override fun wordsDao(): WordsDao

  companion object {
    private var instance: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      if (instance == null) {
        synchronized(AppDatabase::class) {
          instance = Room
            .databaseBuilder(context, AppDatabaseImpl::class.java, "word-cards.db")
            // todo: remove main thread work
            .allowMainThreadQueries()
            .build()
        }
      }
      return instance!!
    }

    fun destroyInstance() {
      instance = null
    }
  }
}