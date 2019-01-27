package ru.alexeypan.wordcards.categories.impl.add

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.alexeypan.wordcards.categories.impl.R
import ru.alexeypan.wordcards.core.ui.dialog.DialogFactory
import ru.alexeypan.wordcards.core.ui.dialog.Immortal
import ru.alexeypan.wordcards.core.ui.dialog.ImmortalDialogWidget
import ru.alexeypan.wordcards.core.ui.state.StateRegistry
import ru.alexeypan.wordcards.core.ui.state.properties.StringProperty

class AddCategoryDialogWidget(
  private val context: Context,
  stateRegistry: StateRegistry,
  lifecycle: Lifecycle
) : DialogFactory, Immortal {

  var addCategoryListener: ((String) -> Unit)? = null
  private val dialogWidget: ImmortalDialogWidget = ImmortalDialogWidget(this, stateRegistry, lifecycle)
  private val categoryProp = StringProperty("category_name", "")

  init {
    stateRegistry.register(categoryProp)
  }

  fun show(categoryName: String = "") {
    categoryProp.put(categoryName)
    dialogWidget.show()
  }

  override fun revival(): Boolean = dialogWidget.revival()

  override fun create(): Dialog {
    val dialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
    val view = LayoutInflater.from(context).inflate(R.layout.add_category_dialog, null)
    val categoryField = view.findViewById<EditText>(R.id.etCategory)
    val closeButton = view.findViewById<ImageView>(R.id.ivClose)
    val readyButton = view.findViewById<TextView>(R.id.tvReady)
    categoryField.setText(categoryProp.get())
    categoryField.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        categoryProp.put(s?.toString())
      }
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
    closeButton.setOnClickListener { dialogWidget.hide() }
    readyButton.setOnClickListener {
      addCategoryListener?.invoke(categoryField.text.toString())
      dialogWidget.hide()
    }
    dialog.setContentView(view)
    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    return dialog
  }

}