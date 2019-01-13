package ru.alexeypan.wordcards.wordlist.impl.di.scope

import ru.alexeypan.wordcards.wordlist.api.WordListModule
import ru.alexeypan.wordcards.wordlist.api.WordListScope

class WordListScopeImpl : WordListScope {

  private var wordListModule: WordListModule? = null

  override fun wordListModule(): WordListModule {
    return wordListModule!!
  }

  override fun open() {
    if (wordListModule == null) {
      wordListModule = WordListModuleImpl()
    }
  }

  override fun close() {
    wordListModule = null
  }
}