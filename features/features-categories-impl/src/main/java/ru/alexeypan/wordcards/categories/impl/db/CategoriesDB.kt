package ru.alexeypan.wordcards.categories.impl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryDB::class], version = 1)
abstract class CategoriesDB : RoomDatabase() {
  abstract fun categoriesDao(): CategoriesDao

  companion object {
    private var instance: CategoriesDB? = null

    fun getInstance(context: Context): CategoriesDB? {
      if (instance == null) {
        synchronized(CategoriesDB::class) {
          instance = Room
            .databaseBuilder(context, CategoriesDB::class.java, "categories.db")
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