package com.example.rescuehub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rescuehub.databinding.FragmentHomeBinding
import com.example.rescuehub.ui.factory.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        /*viewModel.getSession().observe(requireActivity()) {
            if (!it.isLogin && it.isFirstLaunch) {
                // Jika tidak login dan pertama kali buka aplikasi
                startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
            } else if(!it.isLogin && !it.isFirstLaunch) {
                // Jika tidak login namun sudah membuka aplikasi
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}