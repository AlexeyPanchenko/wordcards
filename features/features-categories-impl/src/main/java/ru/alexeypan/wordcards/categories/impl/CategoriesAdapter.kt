package ru.alexeypan.wordcards.categories.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter : RecyclerView.Adapter<CategoryVH>() {

  private val categories = arrayListOf<Category>()

  fun setItems(list: List<Category>) {
    categories.clear()
    categories.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
    return CategoryVH(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))
  }

  override fun onBindViewHolder(holder: CategoryVH, position: Int) {
    holder.title.text = categories[position].title
  }

  override fun getItemCount(): Int = categories.size
}

class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val title: TextView = itemView.findViewById(R.id.tvCategoryTitle)
}