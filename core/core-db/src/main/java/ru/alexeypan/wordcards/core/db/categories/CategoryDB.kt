package ru.alexeypan.wordcards.core.db.categories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoriesTable")
data class CategoryDB(
  @PrimaryKey
  val id: Int,
  val title: String
)