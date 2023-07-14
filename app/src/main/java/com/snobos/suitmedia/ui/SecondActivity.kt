package com.snobos.suitmedia.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.snobos.suitmedia.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val chooseUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedUserName = result.data?.getStringExtra("selectedUserName")
            binding.tvSelectedUsername.text = selectedUserName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val name = intent.getStringExtra("name")
        binding.tvName.text = name

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            chooseUserLauncher.launch(intent)
        }
    }
}