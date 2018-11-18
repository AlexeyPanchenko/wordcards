package ru.alexeypan.wordcards.categories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoriesDao {

  @Query("SELECT * from categoriesTable")
  fun getAll(): List<CategoryDB>

  @Query("SELECT * from categoriesTable WHERE id LIKE :id LIMIT 1")
  fun get(id: Int): CategoryDB

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: CategoryDB)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(category: List<CategoryDB>)
}