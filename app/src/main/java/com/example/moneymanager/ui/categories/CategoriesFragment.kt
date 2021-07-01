package com.example.moneymanager.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moneymanager.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CategoriesFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_categories, container, false)

        val pageAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        val viewPager2: ViewPager2 = root.findViewById(R.id.categories_viewpager)
            viewPager2.adapter = pageAdapter
        val tabLayout: TabLayout = root.findViewById(R.id.categories_tabs)

        val title: ArrayList<String> = arrayListOf("支出", "收入")

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = title[position]
        }.attach()


        return root
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        private var fragments: ArrayList<Fragment> = arrayListOf(
            CategoriesOutcomeFragment(),
            CategoriesIncomeFragment()
        )

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}