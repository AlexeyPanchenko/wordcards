package ru.alexeypan.wordcards.words.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.words_edit_list_fragment.*
import ru.active.life.core.ui.recycler.BaseRecyclerAdapter
import ru.alexeypan.wordcards.core.ui.BaseFragment
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.words.R
import ru.alexeypan.wordcards.words.WordsScope
import ru.alexeypan.wordcards.words.add.AddWordDialog

class WordsEditFragment : BaseFragment() {

  companion object {

    private const val CATEGORY_ID_EXTRA = "category_id_extra"

    fun newInstance(categoryId: Long): WordsEditFragment {
      val fragment = WordsEditFragment()
      val bundle = Bundle()
      bundle.putLong(CATEGORY_ID_EXTRA, categoryId)
      fragment.arguments = bundle
      return fragment
    }
  }

  private val wordsScope: WordsScope = Injector.get(WordsScope::class)
  private val adapter = BaseRecyclerAdapter()
  private val renderer = WordsEditListRenderer()

  private lateinit var dialog: AddWordDialog
  private var categoryId: Long = -1

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view: View = layoutInflater.inflate(R.layout.words_edit_list_fragment, container, false)

    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    categoryId = arguments?.getLong(CATEGORY_ID_EXTRA) ?: -1
    rvList.adapter = adapter
    rvList.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
    rvList.layoutManager = LinearLayoutManager(requireActivity())
    adapter.addRenderers(renderer, wordsScope.wordsStorage.getAll(categoryId))

    fabAdd.setOnClickListener { dialog.show() }
    initAddWordDialog()
  }

  private fun initAddWordDialog() {
    dialog = AddWordDialog(requireContext())
    dialog.setListener { word ->
      wordsScope.wordsStorage.save(word, categoryId)
      wordsScope.categoryCacheCleaner.update(categoryId)
      val position = adapter.itemCount
      adapter.addRenderer(renderer, word)
      adapter.notifyItemInserted(position)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    dialog.hide()
  }

}