package ru.alexeypan.wordcards.core.ui.dialog

interface Immortal {
  /**
   * @return true if it was lively, else false.
   */
  fun revival(): Boolean
}