package ru.alexeypan.wordcards.wordlist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao {

  @Query("SELECT * from wordsTable WHERE categoryId LIKE :categoryId")
  fun getAll(categoryId: Long): List<WordDB>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun save(category: WordDB)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveAll(category: List<WordDB>)
}