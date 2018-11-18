package ru.alexeypan.wordcards.categories.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoriesTable")
data class CategoryDB(
  @PrimaryKey
  val id: Int,
  val title: String
)