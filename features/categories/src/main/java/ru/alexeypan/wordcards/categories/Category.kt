package ru.alexeypan.wordcards.categories

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
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
}