package ru.alexeypan.wordcards.core.db.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoriesDao {

  @Query("SELECT * FROM ${CategoryDB.CATEGORY_TABLE} ORDER BY ${CategoryDB.POSITION} ASC")
  fun getAll(): List<CategoryDB>

  @Query("SELECT * FROM ${CategoryDB.CATEGORY_TABLE} WHERE ${CategoryDB.TITLE} LIKE :title LIMIT 1")
  fun get(title: String): CategoryDB

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: CategoryDB): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(categories: List<CategoryDB>)

  @Query("DELETE FROM ${CategoryDB.CATEGORY_TABLE} WHERE ${CategoryDB.TITLE} = :title")
  fun remove(title: String)
}