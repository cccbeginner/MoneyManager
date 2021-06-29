package com.example.moneymanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moneymanager.MyApplication
import com.example.moneymanager.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val userRepository = (requireActivity().application as MyApplication).userRepository
        homeViewModel = HomeViewModel(userRepository)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val textUsername: TextView = root.findViewById(R.id.home_username)
        val textUserEmail: TextView = root.findViewById(R.id.home_user_email)
        val imageHeadPhoto: ImageView = root.findViewById(R.id.home_head_photo)
        val textTotalIncome: TextView = root.findViewById(R.id.home_total_income)
        val textTotalOutcome: TextView = root.findViewById(R.id.home_total_outcome)
        val textTotalMoney: TextView = root.findViewById(R.id.home_total_money)

        homeViewModel.user.observe(viewLifecycleOwner,  {
            if (it != null) {
                textUsername.text = it.name
                textUserEmail.text = it.email
                Glide.with(requireActivity())
                    .load(it.getPhotoUri())
                    .circleCrop()
                    .into(imageHeadPhoto)
                textTotalIncome.text = it.totalBudget.toString()
                textTotalOutcome.text = it.totalExpense.toString()
                textTotalMoney.text = (it.totalBudget-it.totalExpense).toString()
            }
        })

        return root
    }
}