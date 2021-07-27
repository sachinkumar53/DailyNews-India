package com.sachin.app.dailynews_india

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sachin.app.dailynews_india.ui.NewsFragment

class NewsPagerAdapter(fragmentActivity: FragmentActivity, private val categories: Array<String>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment =
        NewsFragment.newInstance(categories[position])

}