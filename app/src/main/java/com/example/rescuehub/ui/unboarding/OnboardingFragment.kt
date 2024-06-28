package com.example.rescuehub.ui.unboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.rescuehub.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewPager)
        dotsIndicator = view.findViewById(R.id.dotsIndicator)
        val layouts = listOf(
            R.layout.fragment_first_onboarding,
            R.layout.fragment_second_onboarding,
            R.layout.fragment_third_onboarding
        )
        val adapter = OnboardingPagerAdapter(requireActivity(), layouts)
        viewPager.adapter = adapter
        TabLayoutMediator(dotsIndicator, viewPager) { tab, position -> }.attach()

        if (layouts.isNotEmpty()) {
            val lastScreen = layouts.last()
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == layouts.size - 1) {
                        val lastScreenView = view.findViewById<View>(R.id.viewPager)
                        lastScreenView.findViewById<Button>(R.id.btn_start)?.setOnClickListener {
                            // Save that onboarding is completed
                            val sharedPref = requireActivity().getSharedPreferences(
                                "onboarding",
                                AppCompatActivity.MODE_PRIVATE
                            )
                            with(sharedPref.edit()) {
                                putBoolean("onboarding_complete", true)
                                apply()
                            }

                            // Navigate to the main fragment (home fragment in bottom navigation)
                            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                                .navigate(R.id.navigation_home)
                        }
                    }
                }
            })
        }
    }

    companion object {
        private const val ARG_LAYOUT_RES_ID = "layoutResId"

        fun newInstance(layoutResId: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            fragment.arguments = args
            return fragment
        }
    }
}