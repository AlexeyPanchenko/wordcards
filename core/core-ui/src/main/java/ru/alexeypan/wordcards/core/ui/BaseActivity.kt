package ru.alexeypan.wordcards.core.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexeypan.wordcards.core.ui.state.RootStateRegistry

open class BaseActivity : AppCompatActivity() {

  private var rootStateRegistry: RootStateRegistry? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    savedInstanceState?.let { stateRegistry().restoreState(it) }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    stateRegistry().saveState(outState)
  }

  protected fun stateRegistry(): RootStateRegistry {
    if (rootStateRegistry == null) {
      rootStateRegistry = RootStateRegistry()
    }
    return rootStateRegistry!!
  }
}