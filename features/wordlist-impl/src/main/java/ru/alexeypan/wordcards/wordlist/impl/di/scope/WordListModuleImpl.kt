package ru.alexeypan.wordcards.wordlist.impl.di.scope

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.alexeypan.wordcards.wordlist.api.WordListModule
import ru.alexeypan.wordcards.wordlist.api.WordListStarter
import ru.alexeypan.wordcards.wordlist.impl.outside.start.WordListStarterImpl

class WordListModuleImpl : WordListModule {

  var a = 1
  override fun getStarter(activity: AppCompatActivity): WordListStarter {
    Log.d("TTT", "A = $a")
    a++
    return WordListStarterImpl(activity)
  }
}