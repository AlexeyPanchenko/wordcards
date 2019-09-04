package ru.alexeypan.wordcards.categories

import android.os.Parcelable
import androidx.core.util.ObjectsCompat
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category(
  var title: String,
  var position: Int = NO_POSITION,
  var image: String? = null,
  var wordsCount: Int = 0
) : Parcelable {
  companion object {
    private const val NO_POSITION: Int = -1

    fun newCategory(): Category = Category("")
  }

  fun existsId(): Boolean = position != NO_POSITION

  fun copy(): Category =
    Category(
      title = title,
      position = position,
      image = image,
      wordsCount = wordsCount
    )

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val category: Category = other as Category
    return title == category.title
  }

  override fun hashCode(): Int {
    return ObjectsCompat.hashCode(title)
  }
}