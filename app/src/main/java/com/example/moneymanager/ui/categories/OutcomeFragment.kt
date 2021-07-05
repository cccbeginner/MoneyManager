package com.example.moneymanager.ui.categories

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.MyApplication
import com.example.moneymanager.R
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.repository.CategoryRepository


class OutcomeFragment : Fragment() {

    private lateinit var categoryRepository : CategoryRepository
    private lateinit var outcomeViewModel : OutcomeViewModel
    private lateinit var dialog : Dialog
    private lateinit var onClickEvent: OnClickEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // init
        categoryRepository = (requireActivity().application as MyApplication).categoryRepository
        outcomeViewModel = ViewModelProvider(this, OutcomeViewModelFactory(categoryRepository))
            .get(OutcomeViewModel::class.java)
        dialog = Dialog(outcomeViewModel)
        onClickEvent = OnClickEvent(requireContext(), outcomeViewModel, dialog)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_categories_outcome, container, false)

        // set adapter recyclerview
        val recyclerView: RecyclerView = root.findViewById(R.id.categories_outcome_recyclerview)
        val adapter = CategoryAdapter(emptyArray())
        adapter.onClickEvent = onClickEvent
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        // data observer
        outcomeViewModel.categories.observe(viewLifecycleOwner, {
            adapter.updateData(it)
        })
        outcomeViewModel.insertResult.observe(viewLifecycleOwner, {
            if(it == true){
                ToastResponse.insertSuccess(requireContext())
            }else{
                ToastResponse.insertConflict(requireContext())
            }
        })
        outcomeViewModel.updateResult.observe(viewLifecycleOwner, {
            if(it == true){
                ToastResponse.updateSuccess(requireContext())
            }else{
                ToastResponse.updateConflict(requireContext())
            }
        })

        // insert button
        val insertButton: Button = root.findViewById(R.id.categories_outcome_insert_button)
        insertButton.setOnClickListener {
            dialog.insertDialog(requireContext())
        }

        return root;
    }

    private class OnClickEvent(
        private val context: Context,
        private val outcomeViewModel: OutcomeViewModel,
        private val dialog: Dialog
    ) : CategoryAdapter.OnClickEvent {

        override fun onCategoryClick(category: Category) {
            TODO("Not yet implemented")
        }

        override fun onEditClick(category: Category) {
            outcomeViewModel.selectCategory = category
            dialog.updateDialog(context)
        }
    }

    class Dialog(private val categoriesViewModel: OutcomeViewModel){

        fun insertDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val editText = EditText(context) //final一個editText
            builder.setView(editText)
            builder.setTitle("類別名稱")
            builder.setPositiveButton("確定") { _, _ ->
                val title = editText.text.toString().replace("\\s+".toRegex(), "")
                if (title == ""){
                    ToastResponse.cancel(context)
                }else{
                    categoriesViewModel.insertCategory(title)
                }
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }
            builder.create().show()
        }

        fun updateDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val editText = EditText(context) //final一個editText
            builder.setView(editText)
            builder.setTitle("名稱")
            builder.setPositiveButton("確定") { _, _ ->
                val title = editText.text.toString().replace("\\s+".toRegex(), "")
                if (title == ""){
                    ToastResponse.cancel(context)
                }else{
                    categoriesViewModel.updateCategory(title)
                }
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }
            builder.create().show()
        }
    }
}