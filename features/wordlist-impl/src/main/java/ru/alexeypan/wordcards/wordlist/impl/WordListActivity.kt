package ru.alexeypan.wordcards.wordlist.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_list_activity.*
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.db.WordDB
import ru.alexeypan.wordcards.wordlist.db.WordsDao
import ru.alexeypan.wordcards.wordlist.impl.add.AddWordDialog
import ru.alexeypan.wordcards.wordlist.impl.view.slide.SlideLayoutManager
import ru.alexeypan.wordcards.wordlist.impl.view.slide.SlideTouchHelperCallback

class WordListActivity : BaseActivity() {

  companion object {
    private const val CATEGORY_ID = "category_id"

    fun newIntent(context: Context, categoryId: Long): Intent {
      val intent = Intent(context, WordListActivity::class.java)
      intent.putExtra(CATEGORY_ID, categoryId)
      return intent
    }
  }

  lateinit var dao: WordsDao

  override fun onCreate(savedInstanceState: Bundle?) {
    val dbScope: DBScope = Injector.openScope(DBScope::class.java)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    rvList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    val adapter = WordsAdapter()
    rvList.adapter = adapter

    val callback = SlideTouchHelperCallback()
    val helper = ItemTouchHelper(callback)
    val lm = SlideLayoutManager(rvList, helper)
    helper.attachToRecyclerView(rvList)
    rvList.layoutManager = lm

    val categoryId = intent.getLongExtra(CATEGORY_ID, -1)
    Toast.makeText(this, "Id = $categoryId", Toast.LENGTH_SHORT).show()
    dao = dbScope.appDatabase().wordsDao()
    dao.getAll(categoryId).forEach {
      adapter.addItem(Word(it.original, it.translate))
    }
    callback.onSlideEndCallback = { viewHolder, direction ->
      val layoutPosition = viewHolder.layoutPosition
      adapter.removeItem(layoutPosition)
    }

    val dialog = AddWordDialog(this)
    dialog.setListener { word ->
      adapter.addItem(word)
      dao.save(WordDB(categoryId, word.original, word.translate))
    }
    adapter.setListener { word, pos ->
      word.toggleState()
      adapter.notifyItemChanged(pos, word)
    }
    fabAdd.setOnClickListener { dialog.show() }
  }
}