package ru.alexeypan.wordcards.core.ui.dialog

import android.app.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ru.alexeypan.wordcards.core.ui.state.StateRegistry
import ru.alexeypan.wordcards.core.ui.state.properties.BoolProperty

open class ImmortalDialogWidget(
  private val dialogFactory: DialogFactory,
  stateRegistry: StateRegistry,
  lifecycle: Lifecycle
) : DialogWidget, Immortal, LifecycleObserver {

  protected var dialog: Dialog? = null
  private val isShowing: BoolProperty = BoolProperty("immortal_dialog_is_showing", false)

  init {
    lifecycle.addObserver(this)
    stateRegistry.register(isShowing)
  }

  override fun show() {
    dialog?.dismiss()
    dialog = dialogFactory.create()
    dialog?.show()
    isShowing.put(true)
  }

  override fun hide() {
    dialog?.let {
      it.dismiss()
      isShowing.put(false)
      dialog = null
    }
  }

  override fun isShowing(): Boolean = isShowing.get() ?: false

  /**
   * Call it if you want to restore showing state of your [Dialog].
   */
  override fun revival(): Boolean {
    if (isShowing()) {
      show()
      return true
    }
    return false
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() {
    dialog?.dismiss()
  }
}