package ru.alexeypan.wordcards.categories.impl.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.categories.impl.Category
import ru.alexeypan.wordcards.categories.impl.R

class CategoriesAdapter : RecyclerView.Adapter<CategoryVH>() {

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
}

class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
  val title: TextView = itemView.findViewById(R.id.tvCategoryTitle)
  val more: ImageView = itemView.findViewById(R.id.ivMore)
}