package com.example.clea_orange

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityGmailInputBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GmailInputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGmailInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGmailInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmitGmail.setOnClickListener {
            val email = binding.etGmailInput.text.toString().trim()

            if (email.isEmpty()) {
                showErrorDialog("Email tidak boleh kosong")
            } else if (!email.endsWith("@gmail.com")) {
                showErrorDialog("Email harus menggunakan domain @gmail.com")
            } else {
                val intent = Intent(this, RegistrationActivity::class.java)
                intent.putExtra("EXTRA_EMAIL", email)
                startActivity(intent)
            }
        }
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
