package ru.alexeypan.wordcards.categories.impl.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.category_list.*
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R
import ru.alexeypan.wordcards.categories.impl.add.AddCategoryDialogFragment
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
    dao = Injector.appDatabase?.categoriesDao()!!

    dao.getAll().forEach { adapter.addItem(Category(it.id, it.title)) }
    adapter.setClickListener { Injector.wordListScope.wordListModule().getStarter(this).start(it.id) }

    var c = 0
    fabAdd.setOnClickListener {
      AddCategoryDialogFragment.show(supportFragmentManager)
//      val categoryDB = CategoryDB(c++, "Title = $c")
//      Toast.makeText(this, "add $categoryDB", Toast.LENGTH_SHORT).show()
//      dao.save(categoryDB)
//      val categoryDB2 = dao.get(categoryDB.id)
//      adapter.addItem(Category(categoryDB2.id, categoryDB2.title))
    }
  }
}