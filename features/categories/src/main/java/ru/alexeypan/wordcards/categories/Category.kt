package ru.alexeypan.wordcards.categories

import android.os.Parcelable
import androidx.core.util.ObjectsCompat
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category(
  var id: Long,
  var title: String,
  var image: String? = null,
  var wordsCount: Int = 0
) : Parcelable {
  companion object {
    const val NO_ID: Long = -1L

    fun newCategory(): Category = Category(NO_ID,"")
  }

  fun hasId(): Boolean = id != NO_ID

  fun copy(): Category =
    Category(
      id = id,
      title = title,
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