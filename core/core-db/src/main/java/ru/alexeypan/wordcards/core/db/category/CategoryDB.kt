package ru.alexeypan.wordcards.core.db.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexeypan.wordcards.core.db.category.CategoryDB.Companion.CATEGORY_TABLE

@Entity(
  tableName = CATEGORY_TABLE
)
data class CategoryDB(
  @PrimaryKey @ColumnInfo(name = TITLE, index = true) val title: String,
  @ColumnInfo(name = POSITION) val position: Int,
  @ColumnInfo(name = IMAGE) val image: String? = null,
  @ColumnInfo(name = WORDS_COUNT) var wordsCount: Int = 0
) {
  companion object {
    internal const val CATEGORY_TABLE = "categories"
    internal const val TITLE = "title"
    internal const val POSITION = "position"
    internal const val IMAGE = "image"
    internal const val WORDS_COUNT = "words_count"
  }
}