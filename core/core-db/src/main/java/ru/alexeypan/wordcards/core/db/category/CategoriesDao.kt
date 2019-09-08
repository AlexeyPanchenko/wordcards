package ru.alexeypan.wordcards.core.db.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoriesDao {

  @Query("SELECT * FROM ${CategoryDB.CATEGORY_TABLE} ORDER BY ${CategoryDB.POSITION} ASC")
  fun getAll(): List<CategoryDB>

  @Query("SELECT * FROM ${CategoryDB.CATEGORY_TABLE} WHERE ${CategoryDB.ID} = :categoryId")
  fun getCategory(categoryId: Long): CategoryDB

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: CategoryDB): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(categories: List<CategoryDB>)

  @Query("DELETE FROM ${CategoryDB.CATEGORY_TABLE} WHERE ${CategoryDB.ID} = :id")
  fun remove(id: Long)

  @Query("DELETE FROM ${CategoryDB.CATEGORY_TABLE}")
  fun clear()
}