package ru.alexeypan.wordcards.words.dependencies

import ru.alexeypan.wordcards.words.Word

interface WordsStorage {
  fun save(word: Word, categoryId: Long)
  fun getAll(categoryId: Long): List<Word>
}