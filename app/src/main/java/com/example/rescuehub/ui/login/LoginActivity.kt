package com.example.rescuehub.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rescuehub.R
import com.example.rescuehub.data.local.UserModel
import com.example.rescuehub.databinding.ActivityLoginBinding
import com.example.rescuehub.ui.factory.ViewModelFactory
import com.example.rescuehub.ui.main.MainActivity
import com.example.rescuehub.utils.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val factory = ViewModelFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels {
            factory
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (username.isEmpty() || username.isBlank()) {
                binding.edtUsername.error = "tidak boleh kosong"
            }
            if (password.isEmpty() || password.isBlank()) {
                binding.edtPassword.error = "tidak boleh kosong"
            } else {
                viewModel.login(username, password).observe(this) {
                    when (it) {
                        is Result.Error -> showToast(it.error)
                        Result.Loading -> showToast("loading")
                        is Result.Success -> {
                            showToast(it.data.message)
                            val session = UserModel(username, isFirstLaunch = false, isLogin = true)
                            viewModel.saveSession(session)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                    }
                }
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}