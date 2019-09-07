package ru.alexeypan.wordcards.core.db.category.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.words.WordDB

@Entity(
  tableName = CategoryWordDB.TABLE,
  foreignKeys = [
    ForeignKey(
      entity = CategoryDB::class,
      parentColumns = [CategoryDB.ID],
      childColumns = [CategoryWordDB.CATEGORY_ID],
      onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
      entity = WordDB::class,
      parentColumns = [WordDB.ID],
      childColumns = [CategoryWordDB.WORD_ID],
      onDelete = ForeignKey.CASCADE
    )
  ]
)
data class CategoryWordDB(
  @ColumnInfo(name = CATEGORY_ID, index = true) val categoryId: Long,
  @ColumnInfo(name = WORD_ID, index = true) val wordId: Long
) {

  @ColumnInfo(name = ID)
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0

  companion object {
    internal const val TABLE = "category_word"
    internal const val ID = "_id"
    internal const val CATEGORY_ID = "category_id"
    internal const val WORD_ID = "word_id"
  }
}