package ru.alexeypan.wordcards.categories.impl

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.category_list.*


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
      Category("Category1", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category2", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category3", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category4", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category5", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category6", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category7", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category8", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category9", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category10", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category11", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category12", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6")),
      Category("Category13", listOf("Word1", "Word2", "Word3", "Word4", "Word5", "Word6"))
    ))
  }
}