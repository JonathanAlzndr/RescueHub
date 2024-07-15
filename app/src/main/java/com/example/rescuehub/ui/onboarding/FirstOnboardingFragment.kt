package com.example.rescuehub.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rescuehub.R
import com.example.rescuehub.ui.factory.ViewModelFactory

class FirstOnboardingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: OnboardingViewModel by viewModels {
            factory
        }

    }


}