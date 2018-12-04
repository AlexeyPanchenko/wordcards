package ru.alexeypan.wordcards.categories.impl.add

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.injector.Injector


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

  lateinit var dao: CategoriesDao

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    dao = Injector.appDatabase?.categoriesDao()!!
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val editText = EditText(requireActivity()).apply {
      val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
      lp.leftMargin = 24
      lp.rightMargin = 24
      layoutParams = lp
      inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
    }
    return AlertDialog.Builder(requireActivity())
      .setTitle("Новая категория")
      .setView(editText)
      .setPositiveButton("Добавить") { dialog, which -> dao.save(CategoryDB(editText.text.toString())) }
      .setNegativeButton("Отмена") { dialog, which -> }
      .create()
  }
}