package ru.alexeypan.wordcards.words.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.word_list_activity.*
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.core.ui.coroutines.BaseDispatcherProvider
import ru.alexeypan.wordcards.core.ui.toaster.AndroidToaster
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.words.R
import ru.alexeypan.wordcards.words.Word
import ru.alexeypan.wordcards.words.WordsScope
import ru.alexeypan.wordcards.words.add.AddWordDialog
import ru.alexeypan.wordcards.words.ui.adapter.WordsAdapter
import ru.alexeypan.wordcards.words.ui.adapter.WordsProvider
import ru.alexeypan.wordcards.words.view.slide.SlideLayoutManager
import ru.alexeypan.wordcards.words.view.slide.SlideTouchHelperCallback

class WordListActivity : BaseActivity(), WordListView {

  private val wordsScope = Injector.get(WordsScope::class)

  private lateinit var presenter: WordListPresenter
  private lateinit var adapter: WordsAdapter
  private lateinit var dialog: AddWordDialog
  private lateinit var toaster: Toaster

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    val categoryId: Long? = wordsScope.inRoute.getCategoryId(intent)
    if (categoryId == null || categoryId == -1L) {
      finish()
      return
    }
    Toast.makeText(this, "Id = $categoryId", Toast.LENGTH_SHORT).show()
    initList()
    initAddWordDialog()
    toaster = AndroidToaster(this)
    fabAdd.setOnClickListener { presenter.onAddClicked() }
    presenter = WordListPresenter(categoryId, wordsScope.wordsStorage, BaseDispatcherProvider())
    presenter.onVewAttached(this)
  }

  override fun updateList() {
    adapter.notifyDataSetChanged()
  }

  override fun removeCard(position: Int) {
    adapter.notifyItemRemoved(position)
  }

  override fun openAddWord() {
    dialog.show()
  }

  override fun addCard(position: Int) {
    adapter.notifyItemInserted(position)
  }

  override fun updateCard(word: Word, position: Int) {
    adapter.notifyItemChanged(position, word)
  }

  override fun goToCategories() {
    setResult(Activity.RESULT_OK)
    finish()
  }

  override fun toaster(): Toaster = AndroidToaster(this)

  override fun onDestroy() {
    super.onDestroy()
    presenter.onVewDetached()
  }

  private fun initList() {
    adapter = WordsAdapter()
    adapter.setWordClickListener { word, pos -> presenter.onWordClicked(word, pos) }
    adapter.setWordsProvider(object : WordsProvider {
      override fun getWords(): List<Word> = presenter.provideWords()
    })
    val callback = SlideTouchHelperCallback()
    val helper = ItemTouchHelper(callback)
    val layoutManager = SlideLayoutManager(rvList, helper)
    helper.attachToRecyclerView(rvList)
    callback.setSlideEndCallback{ viewHolder, direction ->
      presenter.onCardSlided(viewHolder.adapterPosition, direction)
    }
    rvList.layoutManager = layoutManager
    rvList.adapter = adapter
  }

  private fun initAddWordDialog() {
    dialog = AddWordDialog(this)
    dialog.setListener { word -> presenter.onWordAdded(word) }
  }

  override fun onBackPressed() {
    presenter.onBackPressed()
  }
}