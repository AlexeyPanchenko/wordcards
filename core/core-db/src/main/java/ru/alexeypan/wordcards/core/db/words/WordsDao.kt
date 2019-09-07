package ru.alexeypan.wordcards.core.db.words

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

  @Query("SELECT * from ${WordDB.WORDS_TABLE} WHERE ${WordDB.CATEGORY_ID} = :categoryId")
  fun getAll(categoryId: Long): List<WordDB>

  @Query("SELECT * from ${WordDB.WORDS_TABLE}")
  fun getAll(): List<WordDB>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun upsert(category: WordDB): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(category: List<WordDB>)
}