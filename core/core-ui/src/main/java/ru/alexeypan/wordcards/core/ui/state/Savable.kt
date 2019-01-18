package ru.alexeypan.wordcards.core.ui.state

import android.os.Bundle

interface Savable {
  fun saveState(bundle: Bundle)
  fun restoreState(bundle: Bundle?)
}