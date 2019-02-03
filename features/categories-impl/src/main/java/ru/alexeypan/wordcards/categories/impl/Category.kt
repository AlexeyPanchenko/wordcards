package ru.alexeypan.wordcards.categories.impl

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
  var id: Long,
  var title: String,
  val image: String? = null,
  val wordsCount: Int = 0
) : Parcelable {
  companion object {
    const val NO_ID: Long = -1

    fun newCategory(): Category = Category(NO_ID, "")
  }
}