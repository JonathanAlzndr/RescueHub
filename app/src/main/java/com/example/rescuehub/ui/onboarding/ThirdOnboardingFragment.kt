package com.example.rescuehub.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rescuehub.R
import com.example.rescuehub.ui.factory.ViewModelFactory
import com.example.rescuehub.ui.login.LoginActivity

class ThirdOnboardingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: OnboardingViewModel by viewModels {
            factory
        }

        val btnStart = view.findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            viewModel.saveLaunchStatus(false)
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }

    }
}