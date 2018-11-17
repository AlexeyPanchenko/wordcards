package ru.alexeypan.wordcards.categories.impl

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.category_list.*
import ru.alexeypan.wordcards.core.db.AppDatabase
import ru.alexeypan.wordcards.core.db.categories.CategoriesDao
import ru.alexeypan.wordcards.core.db.categories.CategoryDB
import ru.alexeypan.wordcards.injector.Injector

class CategoriesActivity : AppCompatActivity() {

  lateinit var dao: CategoriesDao

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.category_list)
    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener { Toast.makeText(this, "REE", Toast.LENGTH_SHORT).show() }
    rvList.layoutManager = LinearLayoutManager(this)
    val adapter = CategoriesAdapter()
    rvList.adapter = adapter

    dao = AppDatabase.getInstance(this.applicationContext)?.categoriesDao()!!
    dao.getAll().forEach { adapter.addItem(Category(it.id, it.title)) }

    adapter.setClickListener { Injector.wordListScope.wordListModule().getStarter(this).start(it.id) }

    var c = 0
    fabAdd.setOnClickListener {
      val categoryDB = CategoryDB(c++, "Title =")
      dao.save(categoryDB)
      val categoryDB2 = dao.get(categoryDB.id)
      adapter.addItem(Category(categoryDB2.id, categoryDB2.title + categoryDB2.id))
    }
  }
}