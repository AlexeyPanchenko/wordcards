package ru.alexeypan.wordcards.categories.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoriesTable")
data class CategoryDB(
  val title: String,
  val position: Int,
  val image: String? = null,
  var wordsCount: Int = 0
) {
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0
}