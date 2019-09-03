package ru.alexeypan.wordcards.core.db.words

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import ru.alexeypan.wordcards.core.db.category.CategoryDB
import ru.alexeypan.wordcards.core.db.words.WordDB.Companion.CATEGORY_TITLE
import ru.alexeypan.wordcards.core.db.words.WordDB.Companion.ORIGINAL
import ru.alexeypan.wordcards.core.db.words.WordDB.Companion.TRANSLATE
import ru.alexeypan.wordcards.core.db.words.WordDB.Companion.WORDS_TABLE

@Entity(
  tableName = WORDS_TABLE,
  primaryKeys = [ORIGINAL, TRANSLATE],
  foreignKeys = [ForeignKey(entity = CategoryDB::class, parentColumns = [CategoryDB.TITLE], childColumns = [CATEGORY_TITLE])]
)
data class WordDB(
  @ColumnInfo(name = CATEGORY_TITLE, index = true) var categoryTitle: String,
  @ColumnInfo(name = ORIGINAL) var original: String,
  @ColumnInfo(name = TRANSLATE) var translate: String,
  @ColumnInfo(name = TRANSCRIPTION) var transcription: String = "",
  @ColumnInfo(name = DESCRIPTION) var description: String = "",
  @ColumnInfo(name = IMAGE) var image: String = ""
) {
  companion object {
    internal const val WORDS_TABLE = "words"
    internal const val CATEGORY_TITLE = "category_title"
    internal const val ORIGINAL = "original"
    internal const val TRANSLATE = "translate"
    internal const val TRANSCRIPTION = "transcription"
    internal const val DESCRIPTION = "description"
    internal const val IMAGE = "image"
  }
}