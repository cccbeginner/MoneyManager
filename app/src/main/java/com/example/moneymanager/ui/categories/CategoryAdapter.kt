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
        open fun onCategoryClick(category: Category){}
        open fun onEditClick(category: Category){}
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
        holder.btnEdit.setOnClickListener { onClickEvent.onEditClick(category) }
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val btnCategory: Button = itemView.findViewById(R.id.categories_btn_category)
        val btnEdit: ImageButton = itemView.findViewById(R.id.categories_btn_edit)
    }

    fun updateData(newCategoryArray: Array<Category>){
        this.categoryArray = newCategoryArray
        notifyDataSetChanged()
    }
}