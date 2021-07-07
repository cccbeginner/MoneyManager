package com.example.moneymanager.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.R
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.subcategory.Subcategory

open class SubcategoryAdapter(private var subcategoryArray: Array<Subcategory>) : RecyclerView.Adapter<SubcategoryAdapter.ViewHolder>() {

    lateinit var onClickEvent : OnClickEvent

    interface OnClickEvent{
        fun onSubcategoryClick(subcategory: Subcategory){}
        fun onEditClick(subcategory: Subcategory){}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categories_subcategory_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subcategory = subcategoryArray[position]
        holder.btnSubcategory.text = subcategory.title
        holder.btnSubcategory.setOnClickListener{ onClickEvent.onSubcategoryClick(subcategory) }
        holder.btnEditSubcategory.setOnClickListener { onClickEvent.onEditClick(subcategory) }
    }

    override fun getItemCount(): Int {
        return subcategoryArray.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val btnSubcategory: Button = itemView.findViewById(R.id.categories_btn_subcategory)
        val btnEditSubcategory: ImageButton = itemView.findViewById(R.id.categories_btn_edit_subcategory)
    }

    fun updateData(newSubcategoryArray: Array<Subcategory>){
        this.subcategoryArray = newSubcategoryArray
        notifyDataSetChanged()
    }
}