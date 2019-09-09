package ru.active.life.core.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypan.wordcards.core.ui.recycler.BaseVH
import ru.alexeypan.wordcards.core.ui.recycler.VHRenderer

class BaseRecyclerAdapter : RecyclerView.Adapter<BaseVH>() {

  private val items: LinkedHashMap<Int, ArrayList<VHRenderer<BaseVH, Any>>> = linkedMapOf()
  private val models: ArrayList<Any?> = arrayListOf()
  private val types: ArrayList<Int> = arrayListOf()
  private val renderers: ArrayList<VHRenderer<BaseVH, Any>> = arrayListOf()

  fun addRenderer(renderer: VHRenderer<out BaseVH, *>, model: Any? = null) {
    val type = renderer.javaClass.hashCode()
    val existingRenderers = items[type] ?: arrayListOf()
    existingRenderers.add(renderer as VHRenderer<BaseVH, Any>)
    items[type] = existingRenderers
    models.add(model)
    types.add(type)
    renderers.add(renderer)
  }

  fun addRenderers(renderer: VHRenderer<out BaseVH, *>, models: List<Any?>) {
    models.forEach { addRenderer(renderer, it) }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
    return items.get(viewType)?.get(0)?.onCreateViewHolder(parent, viewType) ?: BaseVH(parent)
  }

  override fun onBindViewHolder(holder: BaseVH, position: Int) {
    renderers.get(position).onBindViewHolder(holder, position, models.get(position))
  }

  override fun getItemCount(): Int {
    return renderers.size
  }

  override fun getItemViewType(position: Int): Int {
    return types[position]
  }

}