package ru.alexeypan.wordcards.core.ui.state.properties

interface ValueProvider<T> {
  fun get(defaultValue: T? = null): T?
  fun put(value: T? = null)
  fun contains(): Boolean
}