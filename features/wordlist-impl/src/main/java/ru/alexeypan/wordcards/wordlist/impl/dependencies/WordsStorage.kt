package ru.alexeypan.wordcards.wordlist.impl.dependencies

import ru.alexeypan.wordcards.wordlist.impl.Word

interface WordsStorage {
  fun save(word: Word, categoryTitle: String)
  fun getAll(categoryTitle: String): List<Word>
}