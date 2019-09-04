package ru.alexeypan.wordcards.words

import ru.alexeypan.wordcards.injector.Scope
import ru.alexeypan.wordcards.words.dependencies.WordsStorage

class WordsScope(
  internal val wordsStorage: WordsStorage
) : Scope {

  val inRoute = WordsInRoute()
}