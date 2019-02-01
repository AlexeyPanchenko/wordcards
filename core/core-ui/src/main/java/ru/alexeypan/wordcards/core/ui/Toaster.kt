package ru.alexeypan.wordcards.core.ui

import androidx.annotation.StringRes

interface Toaster {
  fun show(text: CharSequence)
  fun show(@StringRes text: Int)
}