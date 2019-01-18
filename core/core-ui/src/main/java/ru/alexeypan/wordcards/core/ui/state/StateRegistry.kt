package ru.alexeypan.wordcards.core.ui.state

import ru.alexeypan.wordcards.core.ui.state.properties.StateProperty

interface StateRegistry {
  fun register(property: StateProperty)
  fun unregister(property: StateProperty)
  fun wasRestored(): Boolean
}