package ru.alexeypan.wordcards.wordlist.impl.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.word_list_activity.*
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.core.ui.coroutines.BaseDispatcherProvider
import ru.alexeypan.wordcards.core.ui.toaster.AndroidToaster
import ru.alexeypan.wordcards.core.ui.toaster.Toaster
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.db.WordsDao
import ru.alexeypan.wordcards.wordlist.impl.R
import ru.alexeypan.wordcards.wordlist.impl.Word
import ru.alexeypan.wordcards.wordlist.impl.WordMapper
import ru.alexeypan.wordcards.wordlist.impl.add.AddWordDialog
import ru.alexeypan.wordcards.wordlist.impl.ui.adapter.WordsAdapter
import ru.alexeypan.wordcards.wordlist.impl.ui.adapter.WordsProvider
import ru.alexeypan.wordcards.wordlist.impl.view.slide.SlideLayoutManager
import ru.alexeypan.wordcards.wordlist.impl.view.slide.SlideTouchHelperCallback

class WordListActivity : BaseActivity(), WordListView {

  companion object {
    private const val CATEGORY_ID = "category_id"

    fun newIntent(context: Context, categoryId: Long): Intent {
      val intent = Intent(context, WordListActivity::class.java)
      intent.putExtra(CATEGORY_ID, categoryId)
      return intent
    }
  }

  private lateinit var presenterScope: WordsPresenterScope
  private lateinit var presenter: WordListPresenter
  private lateinit var adapter: WordsAdapter
  private lateinit var dialog: AddWordDialog
  private lateinit var toaster: Toaster

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    val categoryId = intent.getLongExtra(CATEGORY_ID, -1)
    Toast.makeText(this, "Id = $categoryId", Toast.LENGTH_SHORT).show()
    initScopes(categoryId)
    initList()
    initAddWordDialog()
    toaster = AndroidToaster(this)
    fabAdd.setOnClickListener { presenter.onAddClicked() }
    initPresenter()
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

  override fun toaster(): Toaster = toaster

  override fun onDestroy() {
    super.onDestroy()
    presenter.onVewDetached()
    if (isFinishing) {
      Injector.closeScope(WordsPresenterScope::class.java, true)
    }
  }

  private fun initScopes(categoryId: Long) {
    val dbScope: DBScope = Injector.openScope(DBScope::class.java)
    val wordsDao: WordsDao = dbScope.appDatabase().wordsDao()
    val categoriesDao: CategoriesDao = dbScope.appDatabase().categoriesDao()
    presenterScope = Injector.openScope(
      WordsPresenterScope::class.java,
      WordsPresenterScope(categoryId, wordsDao, categoriesDao, WordMapper(), BaseDispatcherProvider())
    )
  }

  private fun initPresenter() {
    presenter = presenterScope.presenter()
    presenter.onVewAttached(this)
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