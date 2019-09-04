package ru.alexeypan.wordcards.core.db.words

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

  @Query("SELECT * from ${WordDB.WORDS_TABLE} WHERE ${WordDB.CATEGORY_TITLE} = :categoryTitle")
  fun getAll(categoryTitle: String): List<WordDB>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: WordDB)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(category: List<WordDB>)
}