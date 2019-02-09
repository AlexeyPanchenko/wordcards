package ru.alexeypan.wordcards.wordlist.impl.ui

import ru.alexeypan.wordcards.core.ui.mvp.BaseView
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.wordlist.impl.Word

interface WordListView : BaseView {
  fun updateList()
  fun removeCard(position: Int)
  fun toaster(): Toaster
  fun openAddWord()
  fun addCard(position: Int)
  fun updateCard(word: Word, position: Int)
}