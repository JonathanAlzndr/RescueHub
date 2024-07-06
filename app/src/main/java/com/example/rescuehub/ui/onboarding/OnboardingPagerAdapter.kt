package com.example.rescuehub.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingPagerAdapter(fragmentActivity: AppCompatActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FirstOnboardingFragment()
            1 -> fragment = SecondOnboardingFragment()
            2 -> fragment = ThirdOnboardingFragment()
        }
        return fragment as Fragment
    }
}