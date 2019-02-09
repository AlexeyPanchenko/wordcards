package ru.alexeypan.wordcards.wordlist.impl

import ru.alexeypan.wordcards.wordlist.db.WordDB

class WordMapper {

  fun fromDB(word: WordDB): Word {
    return Word(word.id, word.original, word.translate)
  }

  fun fromDB(words: List<WordDB>): List<Word> {
    return words.map { word -> fromDB(word) }
  }

  fun toDB(categoryId: Long, word: Word): WordDB {
    return WordDB(categoryId, word.original, word.translate).apply {
      if (word.existsId()) {
        id = word.id
      }
    }
  }
}