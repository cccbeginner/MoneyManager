package com.example.moneymanager.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.model.category.Category

class CategoriesOutcomeAdapter(private var categoryArray: Array<Category>) : RecyclerView.Adapter<CategoriesOutcomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryArray[position])
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val btnCategory: Button = itemView.findViewById(R.id.categories_category)
        fun bind(category: Category){
            btnCategory.text = category.title
        }
    }

    fun changeData(newCategoryArray: Array<Category>){
        this.categoryArray = newCategoryArray
        notifyDataSetChanged()
    }
}