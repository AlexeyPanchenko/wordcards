package ru.alexeypan.wordcards.categories.impl.add

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class AddCategoryDialogFragment : DialogFragment() {

  companion object {
    const val CATEGORY_NAME = "category_name"
    private val TAG = AddCategoryDialogFragment::class.java.simpleName

    fun show(fragmentManager: FragmentManager, category: String = "") {
      if (fragmentManager.findFragmentByTag(TAG) == null) {
        AddCategoryDialogFragment().run {
          arguments = Bundle().apply { putString(CATEGORY_NAME, category) }
          show(fragmentManager, TAG)
        }
      }
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val editText = EditText(requireActivity()).apply {
      val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
      lp.leftMargin = 24
      lp.rightMargin = 24
      layoutParams = lp
    }
    return AlertDialog.Builder(requireActivity())
      .setTitle("Новая категория")
      .setView(editText)
      .setPositiveButton("Добавить") { dialog, which -> }
      .setNegativeButton("Отмена") { dialog, which -> dismiss()}
      .create()
  }
}