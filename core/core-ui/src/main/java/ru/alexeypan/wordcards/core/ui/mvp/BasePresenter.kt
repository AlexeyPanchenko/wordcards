package ru.alexeypan.wordcards.core.ui.mvp

import androidx.annotation.CallSuper

abstract class BasePresenter<VIEW : BaseView> {

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