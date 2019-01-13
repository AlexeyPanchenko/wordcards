package ru.alexeypan.wordcards.wordlist.api

import ru.alexeypan.wordcards.injector.Scope

interface WordListScope : Scope {
  fun wordListModule(): WordListModule
}