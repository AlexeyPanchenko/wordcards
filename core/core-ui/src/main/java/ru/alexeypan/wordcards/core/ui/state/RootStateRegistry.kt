package ru.alexeypan.wordcards.core.ui.state

import android.os.Bundle
import ru.alexeypan.wordcards.core.ui.state.properties.StateProperty

open class RootStateRegistry(protected var bundle: Bundle? = null) : StateRegistry, Savable {

  protected val stateProperties = hashSetOf<StateProperty>()

  override fun register(property: StateProperty) {
    property.restoreState(bundle)
    stateProperties.remove(property)
    stateProperties.add(property)
  }

  override fun unregister(property: StateProperty) {
    stateProperties.remove(property)
  }

  override fun wasRestored(): Boolean = bundle != null

  override fun saveState(bundle: Bundle) {
    this.bundle = bundle
    stateProperties.forEach { it.saveState(bundle) }
  }

  override fun restoreState(bundle: Bundle?) {
    this.bundle = bundle
    stateProperties.forEach { it.restoreState(bundle) }
  }
}