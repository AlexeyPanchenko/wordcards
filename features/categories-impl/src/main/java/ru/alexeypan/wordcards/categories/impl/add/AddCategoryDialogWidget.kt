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
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R
import ru.alexeypan.wordcards.core.ui.dialog.DialogFactory
import ru.alexeypan.wordcards.core.ui.dialog.Immortal
import ru.alexeypan.wordcards.core.ui.dialog.ImmortalDialogWidget
import ru.alexeypan.wordcards.core.ui.state.StateRegistry
import ru.alexeypan.wordcards.core.ui.state.properties.IntProperty
import ru.alexeypan.wordcards.core.ui.state.properties.ParcelableProperty

class AddCategoryDialogWidget(
  private val context: Context,
  stateRegistry: StateRegistry,
  lifecycle: Lifecycle
) : DialogFactory, Immortal {

  private var addCategoryListener: ((category: Category, position: Int?) -> Unit)? = null
  private val dialogWidget: ImmortalDialogWidget = ImmortalDialogWidget(this, stateRegistry, lifecycle)
  private val categoryProp = ParcelableProperty<Category>("category")
  private val positionProp = IntProperty("category_position")

  init {
    stateRegistry.register(categoryProp)
    stateRegistry.register(positionProp)
  }

  fun setAddCategoryListener(listener: (category: Category, position: Int?) -> Unit) {
    addCategoryListener = listener
  }

  fun show(category: Category? = null, position: Int? = null) {
    categoryProp.put(category ?: Category(""))
    positionProp.put(position)
    dialogWidget.show()
  }

  override fun revival(): Boolean = dialogWidget.revival()

  override fun create(): Dialog {
    val dialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
    val view = LayoutInflater.from(context).inflate(R.layout.add_category_dialog, null)
    val categoryField = view.findViewById<EditText>(R.id.etCategory)
    val closeButton = view.findViewById<ImageView>(R.id.ivClose)
    val readyButton = view.findViewById<TextView>(R.id.tvReady)
    categoryField.setText(categoryProp.get()?.title)
    categoryField.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {
        categoryProp.get()?.title = s?.toString() ?: ""
      }
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
    closeButton.setOnClickListener { dialogWidget.hide() }
    readyButton.setOnClickListener {
      addCategoryListener?.invoke(categoryProp.get()!!, positionProp.get())
      dialogWidget.hide()
    }
    dialog.setContentView(view)
    showKeyboard(dialog)
    return dialog
  }

  private fun showKeyboard(dialog: Dialog) {
    dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
  }

}