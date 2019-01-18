package ru.alexeypan.wordcards.core.ui.state.properties

import ru.alexeypan.wordcards.core.ui.state.Savable

interface StateProperty : Savable {
  fun name(): String
}