package ru.alexeypan.wordcards.categories.impl

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.category_list.*
import ru.alexeypan.wordcards.injector.Injector

class CategoriesActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.category_list)
    setSupportActionBar(bottomBar)
    bottomBar.setNavigationOnClickListener { Toast.makeText(this, "REE", Toast.LENGTH_SHORT).show() }
    rvList.layoutManager = LinearLayoutManager(this)
    val adapter = CategoriesAdapter()
    rvList.adapter = adapter
    adapter.setItems(listOf(
      Category(1, "Category1"),
      Category(2, "Category2"),
      Category(3, "Category3"),
      Category(4, "Category4"),
      Category(5, "Category5"),
      Category(6, "Category6"),
      Category(7, "Category7"),
      Category(8, "Category8"),
      Category(9, "Category9"),
      Category(10, "Category10"),
      Category(11, "Category11"),
      Category(12, "Category12"),
      Category(13, "Category13")
    ))
    adapter.setClickListener { Injector.wordListScope.wordListModule().getStarter(this).start(it.id) }

    fabAdd.setOnClickListener {
      bottomBar.fabAlignmentMode = bottomBar.fabAlignmentMode.xor(1)
    }
  }
}