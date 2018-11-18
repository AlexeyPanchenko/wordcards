package ru.alexeypan.wordcards.injector

import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.wordlist.api.WordListScope

object Injector {

  lateinit var wordListScope: WordListScope
  var appDatabase: AppDatabase? = null

}