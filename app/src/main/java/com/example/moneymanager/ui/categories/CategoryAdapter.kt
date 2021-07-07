package com.example.moneymanager.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.model.category.Category

open class CategoryAdapter(private var categoryArray: Array<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    lateinit var onClickEvent : OnClickEvent

    interface OnClickEvent{
        fun onCategoryClick(category: Category){}
        fun onEditClick(category: Category){}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryArray[position]
        holder.btnCategory.text = category.title
        holder.btnCategory.setOnClickListener{ onClickEvent.onCategoryClick(category) }
        holder.btnEditCategory.setOnClickListener { onClickEvent.onEditClick(category) }
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val btnCategory: Button = itemView.findViewById(R.id.categories_btn_category)
        val btnEditCategory: ImageButton = itemView.findViewById(R.id.categories_btn_edit_category)
    }

    fun updateData(newCategoryArray: Array<Category>){
        this.categoryArray = newCategoryArray
        notifyDataSetChanged()
    }
}