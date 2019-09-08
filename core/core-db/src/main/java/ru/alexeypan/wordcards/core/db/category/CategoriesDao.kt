package ru.alexeypan.wordcards.core.db.category

import androidx.room.*

@Dao
abstract class CategoriesDao {

  @Query("SELECT * FROM ${CategoryDB.CATEGORY_TABLE} ORDER BY ${CategoryDB.POSITION} ASC")
  abstract fun getAll(): List<CategoryDB>

  @Query("SELECT * FROM ${CategoryDB.CATEGORY_TABLE} WHERE ${CategoryDB.ID} = :categoryId")
  abstract fun getCategory(categoryId: Long): CategoryDB

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun save(category: CategoryDB): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun saveAll(categories: List<CategoryDB>)

  @Query("UPDATE ${CategoryDB.CATEGORY_TABLE} SET ${CategoryDB.POSITION} = :position WHERE ${CategoryDB.ID} = :categoryId")
  abstract fun updatePosition(categoryId: Long, position: Int)

  @Query("DELETE FROM ${CategoryDB.CATEGORY_TABLE} WHERE ${CategoryDB.ID} = :id")
  abstract fun remove(id: Long)

  @Query("DELETE FROM ${CategoryDB.CATEGORY_TABLE}")
  abstract fun clear()

  @Transaction
  open fun updatePositions(categoryIds: List<Long>) {
    categoryIds.forEachIndexed { index, categoryId -> updatePosition(categoryId, index) }
  }
}