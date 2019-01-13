package ru.alexeypan.wordcards.wordlist.api

import androidx.appcompat.app.AppCompatActivity
import ru.alexeypan.wordcards.injector.Module

interface WordListModule : Module {

  fun getStarter(activity: AppCompatActivity): WordListStarter
}