package ru.alexeypan.wordcards.injector

interface Scope {
  fun open()
  fun close()
}
