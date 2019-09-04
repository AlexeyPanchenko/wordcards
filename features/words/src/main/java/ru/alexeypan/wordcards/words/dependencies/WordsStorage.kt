package ru.alexeypan.wordcards.words.dependencies

import ru.alexeypan.wordcards.words.Word

interface WordsStorage {
  fun save(word: Word, categoryTitle: String)
  fun getAll(categoryTitle: String): List<Word>
}