package ru.alexeypan.wordcards.categories.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter : RecyclerView.Adapter<CategoryVH>() {

  private val categories = arrayListOf<Category>()
  private var categoryClickListener: ((category: Category) -> Unit)? = null

  fun setItems(list: List<Category>) {
    categories.clear()
    categories.addAll(list)
    notifyDataSetChanged()
  }

  fun addItem(category: Category) {
    val position: Int = categories.size
    categories.add(category)
   notifyItemInserted(position)
  }

  fun setClickListener(listener: (category: Category) -> Unit) {
    categoryClickListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
    return CategoryVH(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))
  }

  override fun onBindViewHolder(holder: CategoryVH, position: Int) {
    val category = categories[position]
    holder.title.text = category.title
    holder.itemView.setOnClickListener { categoryClickListener?.invoke(category) }
  }

  override fun getItemCount(): Int = categories.size
}

class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val title: TextView = itemView.findViewById(R.id.tvCategoryTitle)
}