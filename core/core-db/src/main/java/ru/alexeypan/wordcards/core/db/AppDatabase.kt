package ru.alexeypan.wordcards.core.db

import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.wordlist.db.WordsDao

interface AppDatabase {
  fun categoriesDao(): CategoriesDao
  fun wordsDao(): WordsDao

}