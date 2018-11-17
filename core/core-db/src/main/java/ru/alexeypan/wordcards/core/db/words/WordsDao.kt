package ru.alexeypan.wordcards.core.db.words

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

  @Query("SELECT * from wordsTable WHERE categoryId LIKE :categoryId")
  fun getAll(categoryId: Int): List<WordDB>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: WordDB)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(category: List<WordDB>)
}