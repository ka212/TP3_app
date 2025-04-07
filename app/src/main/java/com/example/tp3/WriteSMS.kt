package com.example.tp3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp3.databinding.ActivityWriteSmsBinding

class WriteSMS : AppCompatActivity() {

    private lateinit var binding: ActivityWriteSmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWriteSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.confirmer.setOnClickListener {
            val numero = binding.num.text.toString()
            val message = binding.message.text.toString()

            val intent = Intent(this, ConfirmSMS::class.java)
            intent.putExtra("numero", numero)
            intent.putExtra("message", message)

            startActivityForResult(intent, 1)
        }
    }
}
