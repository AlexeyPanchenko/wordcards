package ru.alexeypan.wordcards.wordlist.impl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordDB::class], version = 1)
abstract class WordsDatabase : RoomDatabase() {
  abstract fun wordsDao(): WordsDao

  companion object {
    private var instance: WordsDatabase? = null

    fun getInstance(context: Context): WordsDatabase? {
      if (instance == null) {
        synchronized(WordsDatabase::class) {
          instance = Room
            .databaseBuilder(context, WordsDatabase::class.java, "words.db")
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