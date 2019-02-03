package ru.alexeypan.wordcards.core.ui.toaster

import androidx.annotation.StringRes

interface Toaster {
  fun show(text: CharSequence)
  fun show(@StringRes text: Int)
}