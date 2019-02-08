package ru.alexeypan.wordcards.core.ui.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides [main] and [background] [CoroutineDispatcher] for easy replacing in presenter.
 */
interface DispatcherProvider {
  val main: CoroutineDispatcher
  val background: CoroutineDispatcher
}

class BaseDispatcherProvider : DispatcherProvider {
  override val main: CoroutineDispatcher
    get() = Dispatchers.Main
  override val background: CoroutineDispatcher
    get() = Dispatchers.IO
}