package ru.alexeypan.wordcards.categories.impl.list.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R
import java.util.*

internal class CategoriesAdapter : RecyclerView.Adapter<CategoryVH>() {

  private val categories = arrayListOf<Category>()
  private var categoryClickListener: ((category: Category) -> Unit)? = null
  private var moreClickListener: ((view: View, category: Category, position: Int) -> Unit)? = null

  fun setItems(list: List<Category>) {
    categories.clear()
    categories.addAll(list)
    notifyDataSetChanged()
  }

  fun clear() {
    categories.clear()
    notifyDataSetChanged()
  }

  fun addItem(category: Category) {
    val position: Int = categories.size
    categories.add(category)
    notifyItemInserted(position)
  }

  fun removeItem(position: Int) {
    categories.removeAt(position)
    notifyItemRemoved(position)
  }

  fun updateItem(category: Category, position: Int) {
    categories[position] = category
    notifyItemChanged(position, category)
  }

  fun moveItems(fromPosition: Int, toPosition: Int) {
    Collections.swap(categories, fromPosition, toPosition)
    notifyItemMoved(fromPosition, toPosition)
  }

  fun setCategoryClickListener(listener: (category: Category) -> Unit) {
    categoryClickListener = listener
  }

  fun setMoreClickListener(listener: (view: View, category: Category, position: Int) -> Unit) {
    moreClickListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
    return CategoryVH(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))
  }

  override fun onBindViewHolder(holder: CategoryVH, position: Int) {
    val category = categories[holder.adapterPosition]
    holder.title.text = category.title
    holder.itemView.setOnClickListener { categoryClickListener?.invoke(category) }
    holder.more.setOnClickListener { moreClickListener?.invoke(it, category, holder.adapterPosition) }
  }

  override fun getItemCount(): Int = categories.size

  override fun onFailedToRecycleView(holder: CategoryVH): Boolean = true
}