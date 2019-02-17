package ru.alexeypan.wordcards.wordlist.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
  tableName = "wordsTable",
  indices = [Index("categoryId")]
)
data class WordDB(
  var categoryId: Long,
  var original: String,
  var translate: String,
  var transcription: String = "",
  var description: String = "",
  var image: String = "",
  var yesCount: Int = 0,
  var noCount: Int = 0
) {
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0
}