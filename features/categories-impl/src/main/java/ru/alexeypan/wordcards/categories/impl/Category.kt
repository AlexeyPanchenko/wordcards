package ru.alexeypan.wordcards.categories.impl

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(val id: Int, val title: String) : Parcelable