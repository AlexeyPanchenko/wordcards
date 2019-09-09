package ru.alexeypan.wordcards.core.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.alexeypan.wordcards.core.ui.state.Savable
import ru.alexeypan.wordcards.core.ui.state.StateProvider
import ru.alexeypan.wordcards.core.ui.state.StateRegistryProvider

open class BaseFragment : Fragment() {

  protected val stateProvider: StateProvider by lazy { StateRegistryProvider() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    savedInstanceState?.let { (stateProvider.stateRegistry() as Savable).restoreState(it) }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    (stateProvider.stateRegistry() as Savable).saveState(outState)
  }
}