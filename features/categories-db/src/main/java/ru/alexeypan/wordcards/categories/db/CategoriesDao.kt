package ru.alexeypan.wordcards.categories.db

import androidx.room.*

@Dao
interface CategoriesDao {

  @Query("SELECT * FROM categoriesTable ORDER BY position ASC")
  fun getAll(): List<CategoryDB>

  @Query("SELECT * FROM categoriesTable WHERE id LIKE :id LIMIT 1")
  fun get(id: Long): CategoryDB

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: CategoryDB): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(categories: List<CategoryDB>)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  fun updateAll(categories: List<CategoryDB>)

  @Query("DELETE FROM categoriesTable WHERE id = :categoryId")
  fun remove(categoryId: Long)
}