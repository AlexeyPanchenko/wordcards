package ru.alexeypan.wordcards.wordlist.impl.scope

import ru.alexeypan.wordcards.wordlist.api.WordListModule
import ru.alexeypan.wordcards.wordlist.api.WordListScope

class WordListScopeImpl : WordListScope {

  override fun wordListModule(): WordListModule {
    return WordListModuleImpl()
  }
}