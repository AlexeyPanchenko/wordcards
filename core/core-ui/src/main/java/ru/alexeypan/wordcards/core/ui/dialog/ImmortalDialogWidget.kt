package ru.alexeypan.wordcards.core.ui.dialog

import android.app.Dialog
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import ru.alexeypan.wordcards.core.ui.state.StateRegistry
import ru.alexeypan.wordcards.core.ui.state.properties.BoolProperty

class ImmortalDialogWidget(
  private val dialogFactory: DialogFactory,
  stateRegistry: StateRegistry,
  lifecycle: Lifecycle
) : DialogWidget, Immortal, DefaultLifecycleObserver {

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

  override fun onDestroy(owner: LifecycleOwner) {
    dialog?.dismiss()
  }
}