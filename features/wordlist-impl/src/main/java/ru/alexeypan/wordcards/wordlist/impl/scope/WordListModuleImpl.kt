package ru.alexeypan.wordcards.wordlist.impl.scope

import androidx.appcompat.app.AppCompatActivity
import ru.alexeypan.wordcards.wordlist.api.WordListModule
import ru.alexeypan.wordcards.wordlist.api.WordListStarter
import ru.alexeypan.wordcards.wordlist.impl.start.WordListStarterImpl

class WordListModuleImpl : WordListModule {

  override fun getStarter(activity: AppCompatActivity): WordListStarter {
    return WordListStarterImpl(activity)
  }
}