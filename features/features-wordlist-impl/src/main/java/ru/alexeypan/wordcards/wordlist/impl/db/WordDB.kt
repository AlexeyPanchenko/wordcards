package ru.alexeypan.wordcards.wordlist.impl.db

import androidx.room.Entity
import androidx.room.Index

@Entity(
  tableName = "wordsTable",
  indices = [Index("categoryId")],
  primaryKeys = ["original", "translate"]
)
data class WordDB(
  val categoryId: Int,
  val original: String,
  val translate: String
)