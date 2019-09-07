package ru.alexeypan.wordcards.core.db.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
  tableName = CategoryDB.CATEGORY_TABLE
)
data class CategoryDB(
  @ColumnInfo(name = TITLE, index = true) val title: String,
  @ColumnInfo(name = POSITION) val position: Int,
  @ColumnInfo(name = IMAGE) val image: String? = null
) {

  @ColumnInfo(name = ID)
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0

  companion object {
    internal const val ID = "_id"
    internal const val CATEGORY_TABLE = "categories"
    internal const val TITLE = "title"
    internal const val POSITION = "position"
    internal const val IMAGE = "image"
  }
}