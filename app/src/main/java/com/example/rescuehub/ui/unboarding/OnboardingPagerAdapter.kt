package com.example.rescuehub.ui.unboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val layouts: List<Int>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = layouts.size
    override fun createFragment(position: Int): Fragment =
        OnboardingFragment.newInstance(layouts[position])

}