package ru.alexeypan.wordcards.categories.impl.list

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.category_list.*
import ru.alexeypan.wordcards.categories.db.CategoriesDao
import ru.alexeypan.wordcards.categories.db.CategoryDB
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R
import ru.alexeypan.wordcards.categories.impl.add.AddCategoryDialogWidget
import ru.alexeypan.wordcards.core.db.scope.DBScope
import ru.alexeypan.wordcards.core.ui.BaseActivity
import ru.alexeypan.wordcards.injector.Injector
import ru.alexeypan.wordcards.wordlist.api.WordListScope

class CategoriesActivity : BaseActivity() {

  lateinit var dao: CategoriesDao

  override fun onCreate(savedInstanceState: Bundle?) {
    val dbScope: DBScope = Injector.openScope(DBScope::class.java)
    val wordListScope: WordListScope = Injector.openScope(WordListScope::class.java)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.category_list)
    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener { Toast.makeText(this, "REE", Toast.LENGTH_SHORT).show() }
    rvList.layoutManager = GridLayoutManager(this, 3)
    val adapter = CategoriesAdapter()
    rvList.adapter = adapter
    dao = dbScope.appDatabase()?.categoriesDao()!!

    dao.getAll().forEach { adapter.addItem(Category(it.id, it.title)) }
    adapter.setCategoryClickListener { wordListScope.wordListModule().getStarter(this).start(it.id) }

    val addCategoryDialog = AddCategoryDialogWidget(this, stateProvider.stateRegistry("dialog"), lifecycle)
    addCategoryDialog.addCategoryListener = {categoryName ->
      dao.save(CategoryDB(categoryName))
      adapter.clear()
      dao.getAll().forEach { adapter.addItem(Category(it.id, it.title)) }
    }
    addCategoryDialog.revival()

    fabAdd.setOnClickListener { addCategoryDialog.show() }
    adapter.setEditClickListener { addCategoryDialog.show(it.title) }
    adapter.setDeleteClickListener { category, position ->
      dao.remove(category.id)
      adapter.notifyItemRemoved(position)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    Injector.closeScope(WordListScope::class.java)
  }
}