package ru.alexeypan.wordcards.words.ui

import ru.alexeypan.wordcards.core.ui.mvp.BaseView
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.words.Word

interface WordListView : BaseView {
  fun updateList()
  fun removeCard(position: Int)
  fun openAddWord()
  fun addCard(position: Int)
  fun updateCard(word: Word, position: Int)
  fun goToCategories()
  fun toaster(): Toaster
}