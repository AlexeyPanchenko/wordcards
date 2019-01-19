package ru.alexeypan.wordcards.core.ui.dialog

interface DialogWidget {
  fun show()
  fun hide()
  fun isShowing(): Boolean
}