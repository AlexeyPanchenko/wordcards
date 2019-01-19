package ru.alexeypan.wordcards.core.ui.state

import android.os.Bundle
import ru.alexeypan.wordcards.core.ui.state.properties.StateProperty

/**
 * @param name unique name for create nested [Bundle].
 */
class NestedStateRegistry(
  private val name: String
) : RootStateRegistry(null), StateProperty {

  override fun saveState(bundle: Bundle) {
    val nestedBundle = Bundle()
    stateProperties.forEach { it.saveState(nestedBundle) }
    bundle.putBundle(name, nestedBundle)
  }

  override fun restoreState(bundle: Bundle?) {
    this.bundle = bundle?.getBundle(name)
    stateProperties.forEach { it.restoreState(this.bundle) }
  }

  override fun name(): String = name
}