package ru.alexeypan.wordcards.wordlist.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.word_list_activity.*
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.db.WordDB
import ru.alexeypan.wordcards.wordlist.db.WordsDao
import ru.alexeypan.wordcards.wordlist.impl.add.AddWordDialog

class WordListActivity : AppCompatActivity() {

  companion object {
    private const val CATEGORY_ID = "category_id"

    fun newIntent(context: Context, categoryId: Int): Intent {
      val intent = Intent(context, WordListActivity::class.java)
      intent.putExtra(CATEGORY_ID, categoryId)
      return intent
    }
  }

  lateinit var dao: WordsDao

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.word_list_activity)
    rvList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(rvList)
    val adapter = WordsAdapter()
    rvList.adapter = adapter

    val categoryId = intent.getIntExtra(CATEGORY_ID, -1)
    dao = Injector.appDatabase?.wordsDao()!!
    dao.getAll(categoryId).forEach { adapter.addItem(Word(it.original, it.translate)) }

    val dialog = AddWordDialog(this)
    dialog.setListener { word ->
      adapter.addItem(word)
      dao.save(WordDB(categoryId, word.original, word.translate))
    }
    fabAdd.setOnClickListener { dialog.show() }
  }
}