package ru.alexeypan.wordcards.wordlist.api

interface WordListStarter {

  companion object {
    const val UPDATE_CATEGORY = 101
  }

  fun start(categoryId: Long, requestCode: Int = UPDATE_CATEGORY)
}