package ru.alexeypan.wordcards.wordlist.api

import androidx.appcompat.app.AppCompatActivity

interface WordListModule {

  fun getStarter(activity: AppCompatActivity): WordListStarter
}