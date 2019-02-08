package ru.alexeypan.wordcards.core.ui.mvp

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import ru.alexeypan.wordcards.core.ui.coroutines.DispatcherProvider

abstract class BasePresenter<VIEW : BaseView>(
  protected val dispatcherProvider: DispatcherProvider
) {

  /** Coroutine Scope for Main Thread  */
  protected val mainScope: CoroutineScope = CoroutineScope(dispatcherProvider.main)
  /** Coroutine Scope for IO Thread  */
  protected val backgroundScope: CoroutineScope = CoroutineScope(dispatcherProvider.background)

  protected var view: VIEW? = null

  @CallSuper
  open fun onVewAttached(view: VIEW) {
    if (this.view == null) {
      this.view = view
    }
  }

  @CallSuper
  open fun onVewDetached() {
    this.view = null
  }
}