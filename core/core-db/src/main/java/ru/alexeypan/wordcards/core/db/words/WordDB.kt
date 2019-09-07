package ru.alexeypan.wordcards.core.db.words

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.words.WordDB.Companion.WORDS_TABLE

@Entity(
  tableName = WORDS_TABLE,

  foreignKeys = [
    ForeignKey(
      entity = CategoryDB::class,
      parentColumns = [CategoryDB.ID],
      childColumns = [WordDB.CATEGORY_ID],
      onDelete = ForeignKey.CASCADE,
      onUpdate = ForeignKey.CASCADE
    )
  ]
)
data class WordDB(
  @ColumnInfo(name = CATEGORY_ID, index = true) var categoryId: Long,
  @ColumnInfo(name = ORIGINAL, index = true) var original: String,
  @ColumnInfo(name = TRANSLATE, index = true) var translate: String,
  @ColumnInfo(name = TRANSCRIPTION) var transcription: String = "",
  @ColumnInfo(name = DESCRIPTION) var description: String = "",
  @ColumnInfo(name = IMAGE) var image: String = ""
) {

  @ColumnInfo(name = ID)
  @PrimaryKey(autoGenerate = true)
  var id: Long = 0

  companion object {
    internal const val ID = "_id"
    internal const val WORDS_TABLE = "words"
    internal const val CATEGORY_ID = "category_id"
    internal const val ORIGINAL = "original"
    internal const val TRANSLATE = "translate"
    internal const val TRANSCRIPTION = "transcription"
    internal const val DESCRIPTION = "description"
    internal const val IMAGE = "image"
  }
}