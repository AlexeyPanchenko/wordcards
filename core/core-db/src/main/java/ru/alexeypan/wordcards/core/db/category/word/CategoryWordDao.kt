package ru.alexeypan.wordcards.core.db.category.word

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryWordDao {

  @Query("SELECT ${CategoryWordDB.WORD_ID} FROM ${CategoryWordDB.TABLE} WHERE ${CategoryWordDB.CATEGORY_ID} = :categoryId")
  fun getWordIds(categoryId: Long): List<Long>

  @Query("SELECT COUNT(${CategoryWordDB.WORD_ID}) FROM ${CategoryWordDB.TABLE} WHERE ${CategoryWordDB.CATEGORY_ID} = :categoryId")
  fun getWordsCount(categoryId: Long): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun upsert(categoryWord: CategoryWordDB)
}