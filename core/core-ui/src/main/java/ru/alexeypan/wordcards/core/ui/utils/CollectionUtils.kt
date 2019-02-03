package ru.alexeypan.wordcards.core.ui.utils

import java.util.*

object CollectionUtils {

  fun moveItem(list: List<*>, from: Int, to: Int) {
    if (from < to) {
      for (i in from until to) {
        Collections.swap(list, i, i + 1)
      }
    } else {
      for (i in from downTo to + 1) {
        Collections.swap(list, i, i - 1)
      }
    }
  }
}