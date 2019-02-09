package ru.alexeypan.wordcards.wordlist.impl.add

import android.app.Dialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import ru.alexeypan.wordcards.wordlist.impl.Word

class AddWordDialog(
  private val context: Context
) {

  private var dialog: Dialog? = null
  private var addCallback: ((word: Word) -> Unit)? = null

  fun show() {
    if (dialog == null) {
      dialog = create()
    }
    dialog!!.show()
  }

  fun hide() {
    if (dialog != null) {
      dialog!!.dismiss()
    }
  }

  fun setListener(callback: ((word: Word) -> Unit)) {
    addCallback = callback
  }

  private fun create(): Dialog {
    val container = LinearLayout(context).apply {
      layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
      ).apply { orientation = LinearLayout.VERTICAL }
    }
    val originalField = EditText(context)
    val translateField = EditText(context)
    container.addView(originalField)
    container.addView(translateField)
    return AlertDialog.Builder(context)
      .setTitle("Добавить слово")
      .setView(container)
      .setPositiveButton(
        "Добавить",
        { d, _ ->
          addCallback?.invoke(Word.newWord(originalField.text.toString(), translateField.text.toString()))
          d.dismiss()
        }
      )
      .setNegativeButton("Отмена", { _, _ -> })
      .create()
  }
}
