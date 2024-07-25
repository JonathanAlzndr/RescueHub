package com.example.rescuehub.ui.upload

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.rescuehub.R
import com.example.rescuehub.databinding.ActivityUploadBinding
import com.example.rescuehub.ui.factory.ViewModelFactory
import com.example.rescuehub.utils.Result
import com.example.rescuehub.utils.getImageUri
import com.example.rescuehub.utils.uriToFile

class UploadActivity : AppCompatActivity() {

    private var currentImageUri: Uri? = null
    private lateinit var binding: ActivityUploadBinding
    private lateinit var viewModel: UploadViewModel
    private lateinit var description: String

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo picker", ": No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Unggah Gambar"
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[UploadViewModel::class.java]


        binding.progressBar.visibility = View.INVISIBLE

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnKirim.setOnClickListener {
            showLoading(true)
            uploadImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri !!)
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.tvDescription.text?.toString() ?: "Ini adalah deskripsi gambar"

            viewModel.uploadImage(imageFile, description).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                        Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showToast(result.data.message)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image uri", "showImage: $it")
            binding.ivPreview.setImageURI(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}


