package ru.alexeypan.wordcards.categories.impl.add

import android.app.Dialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import ru.alexeypan.wordcards.core.ui.dialog.DialogFactory
import ru.alexeypan.wordcards.core.ui.dialog.DialogWidget
import ru.alexeypan.wordcards.core.ui.dialog.Immortal
import ru.alexeypan.wordcards.core.ui.dialog.ImmortalDialogWidget
import ru.alexeypan.wordcards.core.ui.state.StateRegistry

class AddCategoryDialogWidget(
  private val context: Context,
  stateRegistry: StateRegistry,
  lifecycle: Lifecycle
) : DialogFactory, DialogWidget, Immortal {

  var addCategoryListener: ((String) -> Unit)? = null
  private val dialogWidget: ImmortalDialogWidget = ImmortalDialogWidget(this, stateRegistry, lifecycle)

  override fun show() {
    dialogWidget.show()
  }

  override fun hide() {
    dialogWidget.hide()
  }

  override fun isShowing(): Boolean = dialogWidget.isShowing()

  override fun revival(): Boolean = dialogWidget.revival()

  override fun create(): Dialog {
    val editText = EditText(context).apply {
      val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
      lp.leftMargin = 24
      lp.rightMargin = 24
      layoutParams = lp
      inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
    }
    return AlertDialog.Builder(context)
      .setTitle("Новая категория")
      .setView(editText)
      .setPositiveButton("Добавить") { dialog, which -> addCategoryListener?.invoke(editText.text.toString()) }
      .setNegativeButton("Отмена") { dialog, which -> }
      .create()
  }

}