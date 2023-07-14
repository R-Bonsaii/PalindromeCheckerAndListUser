package com.snobos.suitmedia

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.snobos.suitmedia.databinding.ActivityMainBinding
import com.snobos.suitmedia.ui.SecondActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            val name = binding.edtName.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("name", name)
                startActivity(intent)
            } else {
                showToast("Please enter a name")
            }
        }

        binding.btnCheck.setOnClickListener {
            val text = binding.edtPalindrome.text.toString()
            if (text.isNotEmpty()) {
                val normalizedText = text.filter { it.isLetterOrDigit() }.lowercase()
                val isPalindrome = normalizedText == normalizedText.reversed()
                val message = if (isPalindrome) "isPalindrome" else "not palindrome"
                showAlertDialog(message)
            } else {
                showToast("Please enter a word to be checked")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}