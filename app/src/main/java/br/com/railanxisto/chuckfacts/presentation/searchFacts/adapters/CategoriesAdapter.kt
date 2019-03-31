package br.com.railanxisto.chuckfacts.presentation.searchFacts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.railanxisto.chuckfacts.R
import br.com.railanxisto.chuckfacts.domain.Category
import kotlinx.android.synthetic.main.category_item.view.*

class CategoriesAdapter(
    private val listener: OnItemAdapterClickListener
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private val categories = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        categories[position].run {
            holder.setCategory(this)
            holder.setClickListener {
                listener.onItemClick(this)
            }
        }
    }

    fun setCategories(categoriesList: List<Category>) {
        categories.clear()
        categories.addAll(categoriesList)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setCategory(category: Category) {
            itemView.categoryTextView.text = category.name
        }

        fun setClickListener(clickListener: () -> Unit) {
            itemView.setOnClickListener {
                clickListener()
            }
        }
    }

    interface OnItemAdapterClickListener {
        fun onItemClick(category: Category)
    }
}
