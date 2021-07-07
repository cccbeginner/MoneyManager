package com.example.moneymanager.ui.categories

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanager.MyApplication
import com.example.moneymanager.R
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.subcategory.Subcategory
import com.example.moneymanager.repository.CategoryRepository
import com.example.moneymanager.repository.SubcategoryRepository
import com.example.moneymanager.repository.UserRepository


class OutcomeFragment : Fragment() {

    private lateinit var userRepository: UserRepository
    private lateinit var categoryRepository : CategoryRepository
    private lateinit var subcategoryRepository : SubcategoryRepository
    private lateinit var outcomeViewModel : OutcomeViewModel
    private lateinit var categoryDialog : CategoryDialog
    private lateinit var subcategoryDialog : SubcategoryDialog
    private lateinit var categoryOnClickEvent: CategoryOnClickEvent
    private lateinit var subcategoryOnClickEvent: SubcategoryOnClickEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // init
        userRepository = (requireActivity().application as MyApplication).userRepository
        categoryRepository = (requireActivity().application as MyApplication).categoryRepository
        subcategoryRepository = (requireActivity().application as MyApplication).subcategoryRepository
        outcomeViewModel = ViewModelProvider(this, OutcomeViewModelFactory(userRepository, categoryRepository, subcategoryRepository))
            .get(OutcomeViewModel::class.java)
        categoryDialog = CategoryDialog(outcomeViewModel)
        subcategoryDialog = SubcategoryDialog(outcomeViewModel)
        categoryOnClickEvent = CategoryOnClickEvent(requireContext(), outcomeViewModel, categoryDialog)
        subcategoryOnClickEvent = SubcategoryOnClickEvent(requireContext(), outcomeViewModel, subcategoryDialog)

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_categories_outcome, container, false)

        // set adapter recyclerview
        val categoryRecyclerView: RecyclerView = root.findViewById(R.id.categories_outcome_recyclerview)
        val categoryAdapter = CategoryAdapter(emptyArray())
        categoryAdapter.onClickEvent = categoryOnClickEvent
        categoryRecyclerView.adapter = categoryAdapter
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        // set adapter recyclerview
        val subcategoryRecyclerView: RecyclerView = root.findViewById(R.id.subcategories_outcome_recyclerview)
        val subcategoryAdapter = SubcategoryAdapter(emptyArray())
        subcategoryAdapter.onClickEvent = subcategoryOnClickEvent
        subcategoryRecyclerView.adapter = subcategoryAdapter
        subcategoryRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        // data observer
        outcomeViewModel.user.observe(viewLifecycleOwner, {
            outcomeViewModel.fetchCategory()
            outcomeViewModel.selectCategory(null)
        })
        outcomeViewModel.categories.observe(viewLifecycleOwner, {
            categoryAdapter.updateData(it())
        })
        outcomeViewModel.subcategories.observe(viewLifecycleOwner, {
            subcategoryAdapter.updateData(it())
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
        val insertCategoryButton: Button = root.findViewById(R.id.categories_outcome_insert_button)
        insertCategoryButton.setOnClickListener {
            categoryDialog.insertDialog(requireContext())
        }
        val insertSubcategoryButton: Button = root.findViewById(R.id.subcategories_outcome_insert_button)
        insertSubcategoryButton.setOnClickListener {
            subcategoryDialog.insertDialog(requireContext())
        }

        return root
    }

    private class CategoryOnClickEvent(
        private val context: Context,
        private val outcomeViewModel: OutcomeViewModel,
        private val categoryDialog: CategoryDialog
    ) : CategoryAdapter.OnClickEvent {

        override fun onCategoryClick(category: Category) {
            outcomeViewModel.selectCategory(category)
        }

        override fun onEditClick(category: Category) {
            outcomeViewModel.selectCategory(category)
            categoryDialog.editDialog(context)
        }
    }
    private class SubcategoryOnClickEvent(
        private val context: Context,
        private val outcomeViewModel: OutcomeViewModel,
        private val subcategoryDialog: SubcategoryDialog
    ) : SubcategoryAdapter.OnClickEvent {

        override fun onEditClick(subcategory: Subcategory) {
            outcomeViewModel.selectSubcategory(subcategory)
            subcategoryDialog.editDialog(context)
        }
    }

    private class CategoryDialog(private val outcomeViewModel: OutcomeViewModel){

        fun insertDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val editText = EditText(context) //final一個editText
            builder.setView(editText)
            builder.setTitle("類別名稱")
            builder.setPositiveButton("確定") { _, _ ->
                val title = editText.text.toString().replace("\\s+".toRegex(), " ")
                if (title == "" || title == " "){
                    ToastResponse.cancel(context)
                }else{
                    outcomeViewModel.insertCategory(title)
                }
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }
            builder.create().show()
        }

        fun editDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_category_edit, null)
            val editText: EditText = view.findViewById(R.id.category_edit)
            val deleteButton: ImageButton = view.findViewById(R.id.category_btn_delete)

            builder.setView(view)
            builder.setTitle("新的類別名稱")
            builder.setPositiveButton("確定") { _, _ ->
                val title = editText.text.toString().replace("\\s+".toRegex(), "")
                if (title == ""){
                    ToastResponse.cancel(context)
                }else{
                    outcomeViewModel.updateCategory(title)
                }
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }

            val dialog = builder.create()
            deleteButton.setOnClickListener{
                dialog.cancel()
                alertDeleteDialog(context)
            }
            dialog.show()
        }


        private fun alertDeleteDialog(context: Context){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("確認刪除？將會連同此類別以下的子類別和帳一併刪除！")
            builder.setPositiveButton("確定") { _, _ ->
                outcomeViewModel.deleteCategory()
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }
            builder.create().show()
        }
    }


    private class SubcategoryDialog(private val outcomeViewModel: OutcomeViewModel) {

        fun insertDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val editText = EditText(context) //final一個editText
            builder.setView(editText)
            builder.setTitle("類別名稱")
            builder.setPositiveButton("確定") { _, _ ->
                val title = editText.text.toString().replace("\\s+".toRegex(), " ")
                if (title == "" || title == " "){
                    ToastResponse.cancel(context)
                }else{
                    outcomeViewModel.insertSubcategory(title)
                }
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }
            builder.create().show()
        }

        fun editDialog(context: Context) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_category_edit, null)
            val editText: EditText = view.findViewById(R.id.category_edit)
            val deleteButton: ImageButton = view.findViewById(R.id.category_btn_delete)

            builder.setView(view)
            builder.setTitle("新的子類別名稱")
            builder.setPositiveButton("確定") { _, _ ->
                val title = editText.text.toString().replace("\\s+".toRegex(), "")
                if (title == ""){
                    ToastResponse.cancel(context)
                }else{
                    outcomeViewModel.updateSubcategory(title)
                }
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }

            val dialog = builder.create()
            deleteButton.setOnClickListener{
                dialog.cancel()
                alertDeleteDialog(context)
            }
            dialog.show()
        }

        private fun alertDeleteDialog(context: Context){
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("確認刪除？將會連同此子類別以下的帳一併刪除！")
            builder.setPositiveButton("確定") { _, _ ->
                outcomeViewModel.deleteSubcategory()
            }
            builder.setNegativeButton("取消") { _, _ ->
                ToastResponse.cancel(context)
            }
            builder.create().show()
        }
    }
}