package ru.alexeypan.wordcards.categories.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoriesTable")
data class CategoryDB(
  val title: String
) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 0
}