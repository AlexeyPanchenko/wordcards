package ru.alexeypan.wordcards.wordlist.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "wordsTable",
  indices = [Index("categoryId")]
)
data class WordDB(
  val categoryId: Int,
  val original: String,
  val translate: String
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
}