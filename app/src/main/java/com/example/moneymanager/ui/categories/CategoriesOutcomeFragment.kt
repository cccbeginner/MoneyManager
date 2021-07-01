package com.example.moneymanager.ui.categories

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.MyApplication
import com.example.moneymanager.R
import com.example.moneymanager.model.category.Category


class CategoriesOutcomeFragment : Fragment() {

    private val categoryRepository by lazy { (requireActivity().application as MyApplication).categoryRepository }
    val categoriesViewModel by lazy { ViewModelProvider(this, CategoriesViewModelFactory(categoryRepository)).get(CategoriesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_categories_outcome, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.categories_outcome_recyclerview)
        val adapter = CategoriesOutcomeAdapter(emptyArray())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        // data observer
        categoriesViewModel.outcomeCategories.observe(viewLifecycleOwner, {
            adapter.changeData(it)
        })

        // insert button
        val insertButton: Button = root.findViewById(R.id.categories_outcome_insert_button)
        insertButton.setOnClickListener {
            dialog()
        }

        return root;
    }

    private fun dialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext()) //final一個editText
        builder.setView(editText)
        builder.setTitle("類別名稱")
        builder.setPositiveButton("確定") { _, i ->
            categoriesViewModel.insertOutcomeCategory(editText.text.toString())
            Toast.makeText(
                requireContext(),
                "新增類別 ：" + editText.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("取消") { _, i ->
            Toast.makeText(
                requireContext(),
                "取消",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }
}