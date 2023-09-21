package com.example.injectiontest.keystore

import android.os.Build
import androidx.fragment.app.Fragment
import com.example.injectiontest.databinding.FragmentKeystoreBinding
import java.nio.charset.Charset
import javax.inject.Inject

class KeystorePresenter @Inject constructor(
    fragment: Fragment,
    private val viewModel: KeystoreViewModel
) {
    private val binding = FragmentKeystoreBinding.bind(fragment.requireView())

    init {
        binding.originalText.text = "Hola buenas tardes como tamo"
        binding.encryptButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.encryptedText.text = String(viewModel.encrypt(binding.originalText.text.toString()).second, Charset.forName("UTF-8"))
            }
        }
        binding.decryptButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.decryptedText.text = viewModel.decrypt(viewModel.encrypt(binding.originalText.text.toString()))
            }
        }
    }
}