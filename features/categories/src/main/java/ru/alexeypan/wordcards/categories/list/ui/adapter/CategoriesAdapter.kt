package ru.alexeypan.wordcards.categories.list.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.categories.Category
import ru.alexeypan.wordcards.categories.R

internal typealias CategoriesProvider = () -> List<Category>

internal class CategoriesAdapter : RecyclerView.Adapter<CategoryVH>() {

  private lateinit var categoriesProvider: CategoriesProvider

  private var categoryClickListener: ((category: Category) -> Unit)? = null
  private var moreClickListener: ((view: View, category: Category, position: Int) -> Unit)? = null

  fun setCategoryClickListener(listener: (category: Category) -> Unit) {
    categoryClickListener = listener
  }

  fun setMoreClickListener(listener: (view: View, category: Category, position: Int) -> Unit) {
    moreClickListener = listener
  }

  fun setCategoriesProvider(provider: CategoriesProvider) {
    this.categoriesProvider = provider
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
    return CategoryVH(LayoutInflater.from(parent.context).inflate(R.layout.categories_item, parent, false))
  }

  override fun onBindViewHolder(holder: CategoryVH, position: Int) {
    val category = categoriesProvider.invoke()[holder.adapterPosition]
    holder.title.text = holder.itemView.context.getString(R.string.categories_title, category.title, category.wordsCount)
    holder.itemView.setOnClickListener { categoryClickListener?.invoke(category) }
    holder.more.setOnClickListener { moreClickListener?.invoke(it, category, holder.adapterPosition) }
  }

  override fun getItemCount(): Int = categoriesProvider.invoke().size

  override fun onFailedToRecycleView(holder: CategoryVH): Boolean = true
}