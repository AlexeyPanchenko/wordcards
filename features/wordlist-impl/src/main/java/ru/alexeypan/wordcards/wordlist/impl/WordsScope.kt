package ru.alexeypan.wordcards.wordlist.impl

import ru.alexeypan.wordcards.injector.Scope
import ru.alexeypan.wordcards.wordlist.impl.dependencies.WordsStorage

class WordsScope(
  internal val wordsStorage: WordsStorage
) : Scope {

  val inRoute = WordsInRoute()
}