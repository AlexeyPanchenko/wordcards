package ru.alexeypan.wordcards.categories.impl

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(var title: String, val id: Int? = null) : Parcelable